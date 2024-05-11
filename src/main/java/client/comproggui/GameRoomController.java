package client.comproggui;

import Utility.ClientCallback;
import Utility.PlayerUtility;
import Utility.PlayerUtility;
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



    public PlayerUtility serverUtility;

    public ClientCallback clientCallback;

    public ClientCallbackImpl clientCallbackImpl;

    @FXML
    public void initialize() {
//        clientCallback.getLetterChoice();

        String toSetButtonLabel = "lettasdfaersfaebwpvs";
        ArrayList<Button> buttons = new ArrayList<>();


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



        String letters = toSetButtonLabel.toLowerCase().replaceAll("[^a-z]", "");


        for (int i = 0; i < letters.length(); i++) {
            if (i < buttons.size()) {
                buttons.get(i).setText(String.valueOf(letters.charAt(i)));
            } else {

                break;
            }
        }

        // Add event handler for Enter key press
        answerTextField.setOnKeyPressed(event -> { // changed this to the fxid given in openscenbuilder - yves
            if (event.getCode() == KeyCode.ENTER) {
                handleSubmit();
            }
        });
    }



    @FXML
    public void onSubmitButtonClicked(){
        handleSubmit();
    }

    private void handleSubmit() {
        // Get user input from the text field
        String userInput = gameRoomTextField.getText();
        // Print the user input in the inputPrompt
        inputPrompt.setText(userInput);
        // Clear the text field after submission
        gameRoomTextField.clear();
    }

    public PlayerUtility getServerUtility() {
        return serverUtility;
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

    public ClientCallbackImpl getClientCallbackImpl() {
        return clientCallbackImpl;
    }

    public void setClientCallbackImpl(ClientCallbackImpl clientCallbackImpl) {
        this.clientCallbackImpl = clientCallbackImpl;
    }
}