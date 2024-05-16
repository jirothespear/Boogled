package Client_Java.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

public class GameStackPaneController {
    @FXML
    private StackPane stackPane;

    public void endOfGame() throws IOException {
        // Load all views
        Parent roundFinished = FXMLLoader.load(getClass().getResource("/game-finished-view.fxml"));
        Parent results = FXMLLoader.load(getClass().getResource("/end-game-result-view.fxml"));

        // Add all views to the stack pane
        stackPane.getChildren().addAll(roundFinished, results);

        // Initially show the round finished view
        roundFinished.setVisible(true);
        results.setVisible(false);

        // Set the duration to switch views
        Duration waitingRoomDuration = Duration.seconds(5);
        PauseTransition waitingTransition = new PauseTransition(waitingRoomDuration);
        waitingTransition.setOnFinished(e -> {
            // Fade out round finished view and fade in results view
            fadeTransition(roundFinished, results);
        });
        waitingTransition.play();
    }

    private void fadeTransition(Parent from, Parent to) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), from);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), to);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        fadeOut.setOnFinished(e -> {
            from.setVisible(false);
            to.setVisible(true);
            fadeIn.play();
        });

        fadeOut.play();
    }
}
