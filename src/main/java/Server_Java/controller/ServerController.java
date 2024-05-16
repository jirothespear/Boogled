package Server_Java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerController {
    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @FXML
    private Parent root;

    @FXML
    private void onGameSettingsClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/game-settings.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }

    @FXML
    protected void onUserSettingsClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/user-settings.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }

    @FXML
    private void onExitButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}