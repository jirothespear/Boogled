package client.comproggui;

import Utility.ClientCallback;
import Utility.PlayerUtility;
import Utility.PlayerUtility;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import testers.ClientCallbackImpl;

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

    private ClientCallbackImpl clientCallbackImpl;

    private ClientCallback clientCallback;

    private PlayerUtility serverUtility;

    private WaitingRoomController waitingRoomController;



    private String currentUsername;

    private String gameID;



    @FXML
    public void onLeaderboardButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/leaderboard-view.fxml"));

        Parent root = loader.load();
        Scene scene = leaderboardButton.getScene();
        LeaderboardController leaderboardController = loader.getController();

        //leaderboardController.setServerUtility(serverUtility);
        //leaderboardController.setClientCallbackImpl(clientCallbackImpl);
        //leaderboardController.setClientCallback(clientCallback);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/waiting-room-view.fxml"));
        Parent root = loader.load();
        waitingRoomController = loader.getController();

        clientCallbackImpl.setWaitingRoomController(waitingRoomController);

        System.out.println("Start game button clicked");
        System.out.println("Current username: " + currentUsername);

        serverUtility.getQueueTime(currentUsername);

        waitingRoomController.setServerUtility(serverUtility);
        waitingRoomController.setClientCallbackImpl(clientCallbackImpl);
        waitingRoomController.setClientCallback(clientCallback);
        waitingRoomController.setCurrentUser(currentUsername);

//        gameID = serverUtility.getGameID(currentUsername);

//        System.out.println("Game ID Lobby: " + gameID);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
        scene.getStylesheets().add(getClass().getResource("/client/Font.css").toExternalForm());



        // Set waiting duration
        Duration waitingRoomDuration = Duration.seconds(1);
        PauseTransition waitingTransition = new PauseTransition(waitingRoomDuration);
        waitingTransition.play();
    }


    public ClientCallbackImpl getClientCallbackImpl() {
        return clientCallbackImpl;
    }

    public void setClientCallbackImpl(ClientCallbackImpl clientCallbackImpl) {
        this.clientCallbackImpl = clientCallbackImpl;
    }

    public ClientCallback getClientCallback() {
        return clientCallback;
    }

    public void setClientCallback(ClientCallback clientCallback) {
        this.clientCallback = clientCallback;
    }

    public PlayerUtility getServerUtility() {
        return serverUtility;
    }

    public void setServerUtility(PlayerUtility serverUtility) {
        this.serverUtility = serverUtility;
    }

    public WaitingRoomController getWaitingRoomController() {
        return waitingRoomController;
    }

    public void setWaitingRoomController(WaitingRoomController waitingRoomController) {
        this.waitingRoomController = waitingRoomController;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }
}
