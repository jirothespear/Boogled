package client.comproggui;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class LobbyController {

    @FXML
    private Button leaderboardButton;

    @FXML
    private ImageView lobbyBgImage1;

    @FXML
    private ImageView lobbyBgImage2;

    @FXML
    private AnchorPane lobbyPane1;

    @FXML
    private AnchorPane lobbyPane2;

    @FXML
    private AnchorPane mainLobbyPane;

    @FXML
    private Button startGameButton;

    @FXML
    private AnchorPane switchingPane;

    @FXML
    private ImageView tutorialImage;

    @FXML
    private AnchorPane welcomePane;


    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    public void onLeaderboardButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/leaderboard-view.fxml"));
        Scene scene = leaderboardButton.getScene();

        root.translateXProperty().set(scene.getWidth());
        welcomePane.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateXProperty(), 1, Interpolator.DISCRETE);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(event1 -> {
            welcomePane.getChildren().remove(switchingPane);
        });
        timeline.play();
    }

    @FXML
    public void onStartGameButtonClick(ActionEvent event) throws IOException {

        // Load waiting room
        root = FXMLLoader.load(getClass().getResource("/waiting-room-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
        scene.getStylesheets().add(getClass().getResource("/Font.css").toExternalForm());

        // Set waiting duration
        Duration waitingRoomDuration = Duration.seconds(5);
        PauseTransition waitingTransition = new PauseTransition(waitingRoomDuration);
        waitingTransition.setOnFinished(e -> {
            try {
                // Load game view after waiting duration
                loadGameView(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        waitingTransition.play();
    }

    private void loadGameView(Stage stage) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/game-room-view.fxml"));
        Scene gameScene = new Scene(root);
        stage.setScene(gameScene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
        gameScene.getStylesheets().add(getClass().getResource("/Font.css").toExternalForm());
    }

}
