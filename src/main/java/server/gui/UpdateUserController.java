package server.gui;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sqlconnector.DataPB;

import java.io.IOException;

public class UpdateUserController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button updateButton;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    public void initialize(){
        updateButton.disableProperty().bind(Bindings.isEmpty(username.textProperty())
                .or(Bindings.isEmpty(password.textProperty()))
        );
    }
    @FXML
    public void onBackButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("user-settings.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }

    public void onUpdateButtonClick(ActionEvent event) {
        int userId= 0;
        String updatedUsername = username.getText();
        String updatedPassword = password.getText();

        if (DataPB.checkDuplicate(updatedUsername)){
            showErrorAlert("Username already in use", "Please choose another username.");
        }
        else {
            if (DataPB.updateUser(userId, updatedUsername, updatedPassword)){
                showSuccessAlert("Successfully Updated", "The account has been successfully updated.");
            }
            else {
                showErrorAlert("Update Error!", "There was a problem in updating of the account");
            }
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showSuccessAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
