package client.comproggui;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoundStackPaneController {

    private static final Logger LOGGER = Logger.getLogger(RoundStackPaneController.class.getName());

    @FXML
    private StackPane stackPane;

    private Parent roundFinished;
    private Parent results;
    private Parent countdown;

    public void endOfRound() {
        try {
            loadViews();
            initializeViewVisibility();
            startTransitionSequence();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load FXML views", e);
        }
    }

    private void loadViews() throws IOException {
        if (roundFinished == null) {
            roundFinished = loadFXML("/client/round-finished-view.fxml");
        }
        if (results == null) {
            results = loadFXML("/client/end-round-result-view.fxml");
        }
        if (countdown == null) {
            countdown = loadFXML("/client/countdown.fxml");
        }

        stackPane.getChildren().setAll(countdown, results, roundFinished);
    }

    private Parent loadFXML(String resourcePath) throws IOException {
        try {
            return FXMLLoader.load(getClass().getResource(resourcePath));
        } catch (NullPointerException e) {
            LOGGER.log(Level.SEVERE, "FXML file not found: " + resourcePath, e);
            throw new IOException("FXML file not found: " + resourcePath, e);
        }
    }

    private void initializeViewVisibility() {
        roundFinished.setVisible(true);
        results.setVisible(false);
        countdown.setVisible(false);
    }

    private void startTransitionSequence() {
        Duration waitingRoomDuration = Duration.seconds(5);
        createPauseTransition(waitingRoomDuration, roundFinished, results, Duration.seconds(5), countdown).play();
    }

    private PauseTransition createPauseTransition(Duration delay, Parent from, Parent to, Duration nextDelay, Parent nextView) {
        PauseTransition pauseTransition = new PauseTransition(delay);
        pauseTransition.setOnFinished(e -> fadeTransition(from, to, nextDelay, nextView));
        return pauseTransition;
    }

    private void fadeTransition(Parent from, Parent to, Duration nextTransitionDelay, Parent nextView) {
        FadeTransition fadeOut = createFadeTransition(from, 1.0, 0.0);
        FadeTransition fadeIn = createFadeTransition(to, 0.0, 1.0);

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

    private FadeTransition createFadeTransition(Parent node, double fromValue, double toValue) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), node);
        fadeTransition.setFromValue(fromValue);
        fadeTransition.setToValue(toValue);
        return fadeTransition;
    }
}
