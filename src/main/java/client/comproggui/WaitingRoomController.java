
package client.comproggui;

import Utility.ClientCallback;
import Utility.PlayerUtility;
import Utility.PlayerUtility;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import testers.ClientCallbackImpl;

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




                System.out.println("Game letter choice: " + serverUtility.getLetterChoice(serverUtility.getGameID(currentUser)));

                if (gameRoomController.gameLetterChoice == null) {
                    gameRoomController.setGameLetterChoice(serverUtility.getLetterChoice(serverUtility.getGameID(currentUser)));
                } else {
                    System.out.println("gameLetterChoice is not null");
                }

                gameRoomController.setGameID(serverUtility.getGameID(currentUser));
                gameRoomController.setServerUtility(serverUtility);
                gameRoomController.setClientCallback(clientCallback);
                clientCallbackImpl.setGameRoomController(gameRoomController);
                gameRoomController.setClientCallbackImpl(clientCallbackImpl);


                clientCallback.getLetterChoice(serverUtility.getGameID(currentUser));


                Scene gameScene = new Scene(root);
                Stage stage = (Stage) timerLabel.getScene().getWindow();
                stage.setScene(gameScene);
                stage.show();
                stage.centerOnScreen();
                stage.setResizable(false);
                gameScene.getStylesheets().add(getClass().getResource("/Font.css").toExternalForm());

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