package Client_Java.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EndRoundResultController {
//
    @FXML
    private Label pointsRoundLabel;

    @FXML
    private Label winnerOrLoserLabel;

    @FXML
    private Label numberOfWinsLabel;



    public void setResult(boolean isWinner, int points) {
        System.out.println("EndRound Result Set Triggered");
        Platform.runLater(() -> {
            if (isWinner) {
                winnerOrLoserLabel.setText("WINNER!");
            } else {
                winnerOrLoserLabel.setText("TRY AGAIN!");
            }
            pointsRoundLabel.setText(String.valueOf(points));
        });

    }


    public void setNumberOfWins(int wins) {
        numberOfWinsLabel.setText(String.valueOf(wins));
    }

    public void setPoints(int points) {
        pointsRoundLabel.setText(String.valueOf(points));
    }

}
