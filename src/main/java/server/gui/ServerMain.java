package server.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
      //  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("server-view.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/server/server-view.fxml"));
        Scene scene = new Scene(root, 700, 500);
        stage.setTitle("Boggled Server");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // Disable automatic focus traversal for the root node of the scene
        root.requestFocus();
        root.setFocusTraversable(false);
    }

    public static void main(String[] args) {
        launch();
    }
}