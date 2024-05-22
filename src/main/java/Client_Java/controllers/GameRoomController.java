package Client_Java.controllers;

import CORBA_IDL.Utility.ClientCallback;
import CORBA_IDL.Utility.PlayerUtility;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import Client_Java.model.ClientCallbackImpl;

import java.io.IOException;
import java.util.*;

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

    @FXML
    private Label wordValidityPromptLabel;
    public String currentGameUser;
    public String gameChampion = "null";

    public String gameChampionScore = "null";



    public String gameID;
    public String gameLetterChoice;

    public int roundTime;

    private Timer timer;

    private int playerWinCount = 0;

    boolean isWinner = false;

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    private  int roundScore;
    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            wordValidityPromptLabel.setText("");
            try {
                gameLetterChoice = serverUtility.getLetterChoice(gameID);
            } catch (Exception e){
                System.out.println("Choice Error ->");
            }
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
            Stage stage = (Stage) roundLabel.getScene().getWindow();
            stage.setOnCloseRequest(event -> onWindowCloseRequest());
        });
    }
    @FXML
    public void onWindowCloseRequest() {
        Platform.runLater(() -> {
            System.out.println("Window is closing");
//            serverUtility.leaveGame(gameID,currentGameUser);
//            onGameFinished();
            System.exit(0);
        });

    }
    @FXML
    public void onSubmitButtonClicked() {
        Platform.runLater(() -> {
            try {
                System.out.println("Checking word - Submit button triggered");
                System.out.println("Answer: " + answerTextField.getText() + " Game ID: " + gameID + " User: " + currentGameUser);
                String answer = answerTextField.getText();
                serverUtility.checkWord(answer,currentGameUser ,gameID);
                wordValidityPromptLabel.setText("Valid!");
                answerTextField.clear();
                reactivateButtons();
            } catch (Exception  e) {
                answerTextField.clear();
                reactivateButtons();
                wordValidityPromptLabel.setText("Invalid!");
            }

            answerTextField.clear();
            reactivateButtons();

        });

    }


    private void updateTimerLabel() {
        Platform.runLater(() -> {
            timerLabel.setText(String.valueOf(roundTime));
        });
    }

    private void updateRoundLabel() {
        System.out.println("Updating the round count: "+ serverUtility.getRoundCount(gameID) );
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
                    isWinner = false;
                    System.out.println("Round Count: "+ roundLabel.getText());
                    if (!answerTextField.getText().isEmpty() || buttons.stream().anyMatch(Button::isDisabled)) {
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



    public void displayEndRoundResult() {
        Platform.runLater(() -> {
            try {

                //
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/end-round-result-view.fxml"));
                Parent root = loader.load();
                EndRoundResultController controller = loader.getController();
                clientCallbackImpl.setEndRoundResultController(controller);
                roundScore = serverUtility.showScore(currentGameUser,gameID);
                System.out.println("you are the winner "+ isWinner);

                if(isWinner){
                    if (playerWinCount == 0){
                        Label winLabel = new Label("win");
                        controller.setWinLabel(winLabel);
                    }
                    playerWinCount = playerWinCount + 1;
                }
                controller.setPoints(roundScore);
                controller.setResult(isWinner,roundScore);
                controller.setNumberOfWins(playerWinCount);

                System.out.println("Triggered on display EndRoundResult method" + isWinner);
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
    public void onGameFinished(){
        Platform.runLater(() ->{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/end-game-result-view.fxml"));
                Parent root = loader.load();

                EndGameResultController controller = loader.getController();

                if (gameChampion.equals(currentGameUser)){
                    controller.setCongratsOrNextTimeLabel("Congratulations");
                    controller.setWinnerOrLoserLabel("OverAllWinner");
                    controller.setTotalPoints(Integer.parseInt(gameChampionScore));

                }else{

                    controller.setTotalPoints(serverUtility.showScore(currentGameUser,gameID));
                    controller.setCongratsOrNextTimeLabel("That was close");
                    controller.setWinnerOrLoserLabel("Game Over");
                }
                clientCallbackImpl.setGameResultController(controller);
                controller.setClientCallbackImpl(clientCallbackImpl);


                System.out.println("Initializing the End game and calling onGameFinished");
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                stage.centerOnScreen();
                stage.setResizable(false);

                Timer closeTimer = new Timer();
                closeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(stage::close);
                        navigateToLobbyController();
                    }
                }, 8000);
                serverUtility.leaveGame(gameID,currentGameUser);
                Stage gameRoomStage = (Stage) roundLabel.getScene().getWindow();
                gameRoomStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private  void navigateToLobbyController(){
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lobby-view.fxml"));

                Parent root = fxmlLoader.load();

                LobbyController lobbyController = fxmlLoader.getController();
                lobbyController.setCurrentUsername(currentGameUser);
                lobbyController.setClientCallbackImpl(clientCallbackImpl);
                lobbyController.setClientCallback(clientCallback);
                lobbyController.setServerUtility(serverUtility);

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                stage.centerOnScreen();
                stage.setResizable(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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

    public void setGameChampion(String gameChampion, String gameChampionScore) {
        this.gameChampion = gameChampion;
        this.gameChampionScore = gameChampionScore;
        onGameFinished();
    }//
}//