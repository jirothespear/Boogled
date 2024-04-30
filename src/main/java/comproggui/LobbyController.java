package comproggui;

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

import java.io.IOException;

public class LobbyController {

    @FXML
    private Label gameNameLabel;

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
    private Label rule1Label;

    @FXML
    private Label rule2Label;

    @FXML
    private Label rule3Label;

    @FXML
    private Label rule4Label;

    @FXML
    private Button startGameButton;

    @FXML
    private ImageView tutorialImage;

    @FXML
    private AnchorPane tutorialPane;

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    public void onLeaderboardButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/leaderboard-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onStartGameButtonClick(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/game-room-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
