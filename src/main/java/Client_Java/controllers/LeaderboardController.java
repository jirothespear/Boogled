package Client_Java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LeaderboardController {
    @FXML
    private Label firstPlaceUserLabel;

    @FXML
    private Label secondPlaceUserLabel;

    @FXML
    private Label thirdPlaceUserLabel;

    @FXML
    private Label fourthPlaceUserLabel;

    @FXML
    private Label fifthPlaceUserLabel;

    @FXML
    private Label firstPlacePointsLabel;

    @FXML
    private Label secondPlacePointsLabel;

    @FXML
    private Label thirdPlacePointsLabel;

    @FXML
    private Label fourthPlacePointsLabel;

    @FXML
    private Label fifthPlacePointsLabel;

    public void updateLeaderboard(String[] users, int[] points) {
        if (users.length >= 1) {
            firstPlaceUserLabel.setText(users[0]);
            firstPlacePointsLabel.setText("POINTS: " + points[0]);
        }
        if (users.length >= 2) {
            secondPlaceUserLabel.setText(users[1]);
            secondPlacePointsLabel.setText("POINTS: " + points[1]);
        }
        if (users.length >= 3) {
            thirdPlaceUserLabel.setText(users[2]);
            thirdPlacePointsLabel.setText("POINTS: " + points[2]);
        }
        if (users.length >= 4) {
            fourthPlaceUserLabel.setText(users[3]);
            fourthPlacePointsLabel.setText("POINTS: " + points[3]);
        }
        if (users.length >= 5) {
            fifthPlaceUserLabel.setText(users[4]);
            fifthPlacePointsLabel.setText("POINTS: " + points[4]);
        }
    }


}
