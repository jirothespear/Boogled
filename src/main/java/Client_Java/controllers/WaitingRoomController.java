
package Client_Java.controllers;

import CORBA_IDL.Utility.ClientCallback;
import CORBA_IDL.Utility.GameStartException;
import CORBA_IDL.Utility.PlayerUtility;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import Client_Java.model.ClientCallbackImpl;

import java.io.IOException;

public class WaitingRoomController {

    @FXML
    private Label timerLabel = null;

    private int queueTime;
    private Timeline timeline;

    public PlayerUtility serverUtility;

    public ClientCallback clientCallback;

    public ClientCallbackImpl clientCallbackImpl;

    public String gameID;



    public String currentUser;


    public void setQueueTime(int queueTime) {
        Platform.runLater(() -> {
            if (timerLabel == null) {
                System.out.println("timerLabel is null");
            } else {
                if (queueTime <= 0) {
                    onCountdownFinished();
                    System.out.println("Countdown finished");
                } else {
                    this.queueTime = queueTime;
                    System.out.println("Queue time: " + queueTime);
                    updateTimerLabel();
                }
            }
        });




    }


    private void updateTimerLabel() {
        Platform.runLater(() -> {
            if (queueTime <= 0) {
                timerLabel.setText("0");
            } else {
                timerLabel.setText(String.valueOf(queueTime));
            }
        });
    }


    @FXML
    public void onCountdownFinished() {
        Platform.runLater(() -> {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/game-room-view.fxml"));
                Parent root = loader.load();
                GameRoomController gameRoomController = loader.getController();

                gameID = serverUtility.getGameID(currentUser);
                gameRoomController.setGameID(gameID);
                gameRoomController.setCurrentGameUser(currentUser);
                gameRoomController.setServerUtility(serverUtility);
                gameRoomController.setClientCallback(clientCallback);
                clientCallbackImpl.setGameRoomController(gameRoomController);
                gameRoomController.setClientCallbackImpl(clientCallbackImpl);

                Scene gameScene = new Scene(root);
                Stage stage = (Stage) timerLabel.getScene().getWindow();
                stage.setScene(gameScene);
                stage.show();
                stage.centerOnScreen();
                stage.setResizable(false);
                gameScene.getStylesheets().add(getClass().getResource("/Font.css").toExternalForm());
            } catch (Exception e){
                navigateToLobbyController();
            }
        });
    }   // onCountdownFinished

    public void onWindowCloseRequest() {
        Platform.runLater(() -> {
            System.out.println("Window is closing");
            serverUtility.leaveGame(gameID, currentUser);
            System.exit(0);
        });

    }

    private  void navigateToLobbyController(){
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lobby-view.fxml"));

                Parent root = fxmlLoader.load();

                LobbyController lobbyController = fxmlLoader.getController();
                lobbyController.setCurrentUsername(currentUser);
                lobbyController.setClientCallbackImpl(clientCallbackImpl);
                lobbyController.setClientCallback(clientCallback);
                lobbyController.setServerUtility(serverUtility);

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                stage.centerOnScreen();
                stage.setResizable(false);

                Stage waitingRoom = (Stage) timerLabel.getScene().getWindow();
                waitingRoom.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public PlayerUtility getServerUtility() {
        return serverUtility;
    }

    public void setServerUtility(PlayerUtility serverUtility) {
        this.serverUtility = serverUtility;
    }

    public ClientCallback getClientCallback() {
        return clientCallback;
    }

    public void setClientCallback(ClientCallback clientCallback) {
        this.clientCallback = clientCallback;
    }

    public ClientCallbackImpl getClientCallbackImpl() {
        return clientCallbackImpl;
    }

    public void setClientCallbackImpl(ClientCallbackImpl clientCallbackImpl) {
        this.clientCallbackImpl = clientCallbackImpl;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
}