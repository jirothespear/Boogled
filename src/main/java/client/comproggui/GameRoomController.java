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
    private Button submitBUtton;

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

    // No variables for tiles and the text inside it yet
    // The tiles are wrapped inside a HBox; within a StackPane wrapping an ImageView(image of tile background) and a Text(characters/letters)
    // TileUsed is to change tile to gray to let user know that the letter has been used

    @FXML
    private Text inputPrompt; // can be replaced for counter of words inputted so user can keep track in a way
    @FXML
    private TextField gameRoomTextField;
    @FXML
    private Button submitButton;

    private ArrayList<Button> buttons = new ArrayList<>();

    public PlayerUtility serverUtility;

    public ClientCallback clientCallback;

    public ClientCallbackImpl clientCallbackImpl;

    public String currentGameUser;

    public String gameID;
    public String gameLetterChoice;

    public int roundTime;

    private Timer timer;

    private boolean buttonsEnabled = true;
    @FXML
    public void initialize() {
        Platform.runLater(() -> {

            gameLetterChoice = serverUtility.getLetterChoice(gameID);
            System.out.println("Game letter choice: " + gameLetterChoice);
            answerTextField.setEditable(false);
            answerTextField.setCursor(javafx.scene.Cursor.DEFAULT);

            submitButton = new Button();

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


            answerTextField.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    onSubmitButtonClicked();

                }
            });




            submitButton.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {

                    onSubmitButtonClicked();
                }
            });
        });
    }



    @FXML
    public void onSubmitButtonClicked() {
        try {
            System.out.println("Checking word - Submit button triggered");
            serverUtility.checkWord(answerTextField.getText(),currentGameUser ,gameID);

        } catch (InvalidWordException e) {
            throw new RuntimeException(e);
        }
        handleSubmit();
    }

    private void handleSubmit() {

        String userInput = gameRoomTextField.getText();
        System.out.println("User input: " + userInput);
        inputPrompt.setText(userInput);
        gameRoomTextField.clear();
        answerTextField.clear();
        reactivateButtons();
    }

    private void updateTimerLabel() {
        Platform.runLater(() -> {
                timerLabel.setText(String.valueOf(roundTime));
        });
    }

    private void reactivateButtons() {
        for (Button button : buttons) {
            System.out.println("Button reactivated");
            button.setDisable(false); // reactivates buttons
        }
    }

    public void setRoundTime(int roundTime) {
          Platform.runLater(() -> {
                if (timerLabel == null) {
                    System.out.println("timerLabel is null");
                } else {
                    if (roundTime == 0) {
//                        new Thread(() -> {
//                            try {
//                                while (true) {
//                                    Thread.sleep(3000);
//                                    Platform.runLater(this::initialize);
//                                }
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }).start();

                        if (timer != null) {
                            timer.cancel();
                        }

                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
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

    @FXML
    public void onRoundFinished(){
        try {
            // Load the main application view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/round-stack-pane-view.fxml"));
            System.out.println("Loading FXML: " + getClass().getResource("/round-stack-pane.fxml-view"));
            Parent root = loader.load();
            System.out.println("FXML Loaded: " + (root != null));

            // Create a new scene with the main app view
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Font2.css").toExternalForm());

            // Set the scene on the stage
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
            stage.setResizable(false);

            // Retrieve the controller and initialize the main app stage
            RoundStackPaneController controller = loader.getController();
            System.out.println("Controller: " + controller);
            if (controller != null) {
                controller.endOfRound();
            } else {
                System.out.println("Controller is null");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load FXML file: " + e.getMessage());
        }
    }

    @FXML
    public void onGameFinished(){
        try {
            // Load the main application view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game-stack-pane-view.fxml"));
            System.out.println("Loading FXML: " + getClass().getResource("/game-stack-pane.fxml-view"));
            Parent root = loader.load();
            System.out.println("FXML Loaded: " + (root != null));

            // Create a new scene with the main app view
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Font2.css").toExternalForm());

            // Set the scene on the stage
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
            stage.setResizable(false);

            // Retrieve the controller and initialize the main app stage
            GameStackPaneController controller = loader.getController();
            System.out.println("Controller: " + controller);
            if (controller != null) {
                controller.endOfGame();
            } else {
                System.out.println("Controller is null");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load FXML file: " + e.getMessage());
        }
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