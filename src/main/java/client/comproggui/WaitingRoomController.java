
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



    public void setQueueTime(int queueTime) {

        if (timerLabel == null) {
            System.out.println("timerLabel is null");
        } else {
            this.queueTime = queueTime;
            timerLabel.setText(String.valueOf(queueTime));
            System.out.println("Queue time: " + queueTime);
            startCountdown();
        }
    }

    private void startCountdown() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            queueTime--;
            updateTimerLabel();
            if (queueTime <= 0) {
                timeline.stop();

                onCountdownFinished();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateTimerLabel() {
        timerLabel.setText(String.valueOf(queueTime));
    }


    @FXML
    public void onCountdownFinished() {
        Platform.runLater(() -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/game-room-view.fxml"));
                Parent root = loader.load();
                GameRoomController gameRoomController = loader.getController();

                gameRoomController.setServerUtility(serverUtility);
                gameRoomController.setClientCallback(clientCallback);
                gameRoomController.setClientCallbackImpl(clientCallbackImpl);

                Scene gameScene = new Scene(root);
                Stage stage = (Stage) timerLabel.getScene().getWindow();
                stage.setScene(gameScene);
                stage.show();
                stage.centerOnScreen();
                stage.setResizable(false);
                gameScene.getStylesheets().add(getClass().getResource("/Font.css").toExternalForm());

                // Other UI modifications as needed
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
}