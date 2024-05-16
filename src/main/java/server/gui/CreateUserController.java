package server.gui;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import sqlconnector.DataPB;

import java.io.IOException;

public class CreateUserController {
    @FXML
    private TextField userName;
    @FXML
    private TextField userPassword;
    @FXML
    private Button createButton;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    public void initialize(){
        createButton.disableProperty().bind(Bindings.isEmpty(userName.textProperty())
                .or(Bindings.isEmpty(userPassword.textProperty()))
        );
    }

    public void onCreateUserClick(ActionEvent event) throws IOException {
        String username = userName.getText();
        String password = userName.getText();

        if (DataPB.checkDuplicate(username)) {
            showErrorAlert("Username already used", "Please choose another username");
        }
        else {
            if (DataPB.createUser(username, password)) {
                showSuccessAlert("Successfully Registered", "User: " + username + " created!");
                userName.clear();
                userPassword.clear();
            }
            else {
                showErrorAlert("User Registration Failed", "Please try again later!");
            }
        }

        userName.clear();
        userPassword.clear();
    }
    @FXML
    public void onBackButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/server/user-settings.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showSuccessAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
