package client.comproggui;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

public class RoundStackPaneController {
    @FXML
    private StackPane stackPane;

    public void endOfRound() throws IOException {
        // Load all views
        Parent roundFinished = FXMLLoader.load(getClass().getResource("/round-finished-view.fxml"));
        Parent results = FXMLLoader.load(getClass().getResource("/end-round-result-view.fxml"));
        Parent countdown = FXMLLoader.load(getClass().getResource("/countdown.fxml"));

        // Add all views to the stack pane
        stackPane.getChildren().addAll(countdown, results, roundFinished);

        // Initially show the waiting room
        roundFinished.setVisible(true);
        results.setVisible(false);
        countdown.setVisible(false);

        // Set the duration to switch views
        Duration waitingRoomDuration = Duration.seconds(5);
        PauseTransition waitingTransition = new PauseTransition(waitingRoomDuration);
        waitingTransition.setOnFinished(e -> {
            // Fade out waiting room and fade in game room
            fadeTransition(roundFinished, results, Duration.seconds(5), countdown);
        });
        waitingTransition.play();
    }

    private void fadeTransition(Parent from, Parent to, Duration nextTransitionDelay, Parent nextView) {
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
            if (nextTransitionDelay != null && nextView != null) {
                PauseTransition nextTransition = new PauseTransition(nextTransitionDelay);
                nextTransition.setOnFinished(event -> fadeTransition(to, nextView, null, null));
                nextTransition.play();
            }
        });

        fadeOut.play();
    }
}
