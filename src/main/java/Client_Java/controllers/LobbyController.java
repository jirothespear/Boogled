package Client_Java.controllers;

import CORBA_IDL.Utility.ClientCallback;
import CORBA_IDL.Utility.LogoutException;
import CORBA_IDL.Utility.PlayerUtility;
import javafx.animation.*;
import javafx.application.Platform;
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
import Client_Java.model.ClientCallbackImpl;

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
    private Button logoutButton;

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



    @FXML
    public void onLeaderboardButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/leaderboard-view.fxml"));

        Parent root = loader.load();
        Scene scene = leaderboardButton.getScene();
        LeaderboardController leaderboardController = loader.getController();


        String[] usernames = serverUtility.getLeaderboardUsernames();
        int[] points = serverUtility.getLeaderboardPoints();

        leaderboardController.updateLeaderboard(usernames, points);

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/waiting-room-view.fxml"));
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

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
        scene.getStylesheets().add(getClass().getResource("/Font.css").toExternalForm());


        Duration waitingRoomDuration = Duration.seconds(1);
        PauseTransition waitingTransition = new PauseTransition(waitingRoomDuration);
        waitingTransition.play();
    }
    @FXML
    public void onLogoutButtonClick(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login-view.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();
            loginController.setServerUtility(serverUtility);
            loginController.setClientCallbackImpl(clientCallbackImpl);
            loginController.setClientCallback(clientCallback);

            serverUtility.logout(clientCallback,currentUsername);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Textfield.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // This method is called when the window is closed

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) lobbyPane1.getScene().getWindow();
            stage.setOnCloseRequest(event -> onWindowCloseRequest());
        });
    }
    @FXML
    public void onWindowCloseRequest() {
        Platform.runLater(() -> {
            try {
                System.out.println("Window is closing");
                serverUtility.logout(clientCallback, currentUsername);
                System.exit(0);
            } catch (LogoutException e) {
                e.printStackTrace();
            }
        });
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

    public void setServerUtility(PlayerUtility serverUtility) {
        this.serverUtility = serverUtility;
    }


    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public void shutdown () {

        stage.setOnHiding(event -> {
            try {
                serverUtility.logout(clientCallback, currentUsername);
            } catch (LogoutException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
