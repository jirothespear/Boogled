package client.comproggui;

import Utility.ClientCallback;
import Utility.InvalidWordException;
import Utility.PlayerUtility;
import Utility.PlayerUtility;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import testers.ClientCallbackImpl;

import java.util.ArrayList;

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

    private boolean buttonsEnabled = true;
    @FXML
    public void initialize() {
        Platform.runLater(() -> {

            gameLetterChoice = serverUtility.getLetterChoice(gameID);
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


            answerTextField.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    onSubmitButtonClicked();
                }
            });


        });
    }



    @FXML
    public void onSubmitButtonClicked(){
        try {
            System.out.println("Checking word");
            serverUtility.checkWord(answerTextField.getText(),currentGameUser ,gameID);
        } catch (InvalidWordException e) {
            throw new RuntimeException(e);
        }
        handleSubmit();
    }

    private void handleSubmit() {
        reactivateButtons();
        String userInput = gameRoomTextField.getText();
        inputPrompt.setText(userInput);
        gameRoomTextField.clear();
    }

    private void updateTimerLabel() {
        Platform.runLater(() -> {
            if (roundTime <= 0) {
                timerLabel.setText("0");
            } else {
                timerLabel.setText(String.valueOf(roundTime));
            }
        });
    }

    private void reactivateButtons() {
        for (Button button : buttons) {
            System.out.println("Button reactivated");
            button.setDisable(false); // reactivates buttons
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

    public void setRoundTime(int roundTime) {
        if (timerLabel == null) {
            System.out.println("timerLabel is null");
        } else {
            if (roundTime <= 0) {
                System.out.println("Round finished");
            } else {
                this.roundTime = roundTime;
                System.out.println("Round time: " + roundTime);
                updateTimerLabel();
            }
        }
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