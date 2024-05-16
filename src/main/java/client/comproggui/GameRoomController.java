package client.comproggui;

import Utility.ClientCallback;
import Utility.InvalidWordException;
import Utility.PlayerUtility;
import Utility.PlayerUtility;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import server.controller.User;
import testers.ClientCallbackImpl;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameRoomController {

    @FXML
    private TextField answerTextField;

    @FXML
    private Label boggledLabel;

    @FXML
    private Button eighteenthButton;

    @FXML
    private Button eighthButton;

    @FXML
    private Button eleventhButton;

    @FXML
    private Button fifteenthButton;

    @FXML
    private Button fifthButton;

    @FXML
    private Button firstButton;

    @FXML
    private Button fourteenthButton;

    @FXML
    private Button fourthButton;

    @FXML
    private Button nineteenthButton;

    @FXML
    private Button ninthButton;

    @FXML
    private Label roundLabel;

    @FXML
    private Button secondButton;

    @FXML
    private Button seventeenthButton;

    @FXML
    private Button seventhButton;

    @FXML
    private Button sixteenthButton;

    @FXML
    private Button sixthButton;

    @FXML
    private Button submitButton;

    @FXML
    private Button tenthButton;

    @FXML
    private Button thirdButton;

    @FXML
    private Button thirteenthButton;

    @FXML
    private Label timerLabel;

    @FXML
    private Button twelfthButton;

    @FXML
    private Button twentiethButton;

    @FXML
    private Text inputPrompt; // can be replaced for counter of words inputted so user can keep track in a way
    @FXML
    private TextField gameRoomTextField;


    private ArrayList<Button> buttons = new ArrayList<>();

    public PlayerUtility serverUtility;

    public ClientCallback clientCallback;

    public ClientCallbackImpl clientCallbackImpl;



    public String currentGameUser;

    public String gameID;
    public String gameLetterChoice;

    public int roundTime;

    private Timer timer;


    boolean isWinner = false;

    private  int roundScore;
    @FXML
    public void initialize() {
        Platform.runLater(() -> {

            gameLetterChoice = serverUtility.getLetterChoice(gameID);
            System.out.println("Game letter choice: " + gameLetterChoice);
            answerTextField.setEditable(false);
            answerTextField.setCursor(javafx.scene.Cursor.DEFAULT);


            buttons.add(firstButton);
            buttons.add(secondButton);
            buttons.add(thirdButton);
            buttons.add(fourthButton);
            buttons.add(fifthButton);
            buttons.add(sixthButton);
            buttons.add(seventhButton);
            buttons.add(eighthButton);
            buttons.add(ninthButton);
            buttons.add(tenthButton);
            buttons.add(eleventhButton);
            buttons.add(twelfthButton);
            buttons.add(thirteenthButton);
            buttons.add(fourteenthButton);
            buttons.add(fifteenthButton);
            buttons.add(sixteenthButton);
            buttons.add(seventeenthButton);
            buttons.add(eighteenthButton);
            buttons.add(nineteenthButton);
            buttons.add(twentiethButton);



            String letters = gameLetterChoice.toLowerCase().replaceAll("[^a-z]", "");

            for (int i = 0; i < letters.length(); i++) {
                if (i < buttons.size()) {
                    buttons.get(i).setText(String.valueOf(letters.charAt(i)));
                } else {
                    break;
                }
            }

            for (Button button : buttons) {
                button.setOnAction(event -> {
                    answerTextField.appendText(button.getText());
                    button.setDisable(true);

                });
            }
            submitButton.setOnAction(event -> {
                System.out.println("Submit button clicked");
                onSubmitButtonClicked();
            });

        });
    }


    @FXML
    public void onSubmitButtonClicked() {
        try {
            System.out.println("Checking word - Submit button triggered");
            System.out.println("Answer: " + answerTextField.getText() + " Game ID: " + gameID + " User: " + currentGameUser);
            String answer = answerTextField.getText();
            serverUtility.checkWord(answer,currentGameUser ,gameID);

        } catch (InvalidWordException e) {
            throw new RuntimeException(e);
        }
        handleSubmit();
    }

    private void handleSubmit() {

        String userInput = answerTextField.getText();
        System.out.println("User input: " + userInput);
        answerTextField.clear();
        reactivateButtons();
    }

    private void updateTimerLabel() {
        Platform.runLater(() -> {
            timerLabel.setText(String.valueOf(roundTime));
        });
    }

    private void updateRoundLabel() {


            roundLabel.setText("Round: " + serverUtility.getRoundCount(gameID) );

    }

    private void reactivateButtons() {
        for (Button button : buttons) {
            button.setDisable(false);
        }
    }

    public void setRoundTime(int roundTime) {
        Platform.runLater(() -> {
            if (timerLabel == null) {
                System.out.println("timerLabel is null");
            } else {
                updateRoundLabel();
                if (roundTime == 0) {

                    if (answerTextField.getText().isEmpty() == false || buttons.stream().anyMatch(Button::isDisabled)) {
                        answerTextField.clear();
                        reactivateButtons();
                    }

                    if (timer != null) {
                        timer.cancel();
                    }

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            roundScore = serverUtility.showScore(gameID, currentGameUser);
                            System.out.println(roundScore);
                            displayEndRoundResult();

                            Platform.runLater(() -> {
                                initialize();
                                timer.cancel();

                            });
                        }
                    }, 3000);

                    System.out.println("Round finished");
                } else {
                    this.roundTime = roundTime;
                    System.out.println("Round time: " + roundTime);
                    updateTimerLabel();
                }
            }
        });
    }



    private void displayEndRoundResult() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/end-round-result-view.fxml"));
                Parent root = loader.load();

                EndRoundResultController controller = loader.getController();

                controller.setResult(isWinner,roundScore); // Example values, you should set actual result data

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                Timer closeTimer = new Timer();
                closeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(stage::close);
                    }
                }, 3000);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    public void onRoundFinished(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/round-stack-pane-view.fxml"));
            Parent root = loader.load();
            RoundStackPaneController controller = loader.getController();
            clientCallbackImpl.setRoundStackPaneController(controller);
            if (controller != null) {
                controller.endOfRound();
            }
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Font2.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onGameFinished(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game-stack-pane-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Font2.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
            stage.setResizable(false);
            GameStackPaneController controller = loader.getController();
            if (controller != null) {
                controller.endOfGame();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getCurrentGameUser() {
        return currentGameUser;
    }
    public void setServerUtility(PlayerUtility serverUtility) {
        this.serverUtility = serverUtility;
    }

    public ClientCallback getClientCallback() {
        return clientCallback;
    }

    public void setClientCallback(ClientCallback clientCallback) {
        this.clientCallback = clientCallback;
    }


    public void setGameLetterChoice(String gameLetterChoice) {
        this.gameLetterChoice = gameLetterChoice;
    }

    public int getRoundTime() {
        return roundTime;
    }

    public void setClientCallbackImpl(ClientCallbackImpl clientCallbackImpl) {
        this.clientCallbackImpl = clientCallbackImpl;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }
    public void setCurrentGameUser(String currentGameUser) {
        this.currentGameUser = currentGameUser;
    }
}