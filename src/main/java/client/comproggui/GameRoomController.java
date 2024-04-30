package client.comproggui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

public class GameRoomController {

    // No variables for tiles and the text inside it yet
    // The tiles are wrapped inside a HBox; within a StackPane wrapping an ImageView(image of tile background) and a Text(characters/letters)
    // TileUsed is to change tile to gray to let user know that the letter has been used

    @FXML
    private Text inputPrompt; // can be replaced for counter of words inputted so user can keep track in a way
    @FXML
    private TextField gameRoomTextField;
    @FXML
    private Button submitButton;

    @FXML
    public void initialize() {
        // Add event handler for Enter key press
        gameRoomTextField.setOnKeyPressed(event -> {
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
}