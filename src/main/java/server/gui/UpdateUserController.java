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

    private String initialUsername;

    private String initialPassword;

    private int userId;

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
        userId = UpdateUserStorage.getUserId();
        username.setText(UpdateUserStorage.getUsername());
        initialUsername = UpdateUserStorage.getUsername();
        password.setText(UpdateUserStorage.getPassword());
        initialPassword = UpdateUserStorage.getPassword();
        updateButton.disableProperty().bind(Bindings.isEmpty(username.textProperty())
                .or(Bindings.isEmpty(password.textProperty()))
        );


    }
    @FXML
    public void onBackButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/server/user-settings.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        root.requestFocus();

    }

    public void onUpdateButtonClick(ActionEvent event) {

        String updatedUsername = username.getText();
        String updatedPassword = password.getText();

        if (initialUsername.equals(updatedUsername) && !initialPassword.equals(updatedPassword)){

                if (DataPB.updateUser(userId, updatedUsername, updatedPassword)){
                    showSuccessAlert("Successfully Updated", "The account has been successfully updated.");
                }
                else {
                    showErrorAlert("Update Error!", "There was a problem in updating of the account");
                }


        } else if (!initialUsername.equals(updatedUsername) && initialPassword.equals(updatedPassword))

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
        } else {
            showErrorAlert("No Update", "Please update a field!");
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
