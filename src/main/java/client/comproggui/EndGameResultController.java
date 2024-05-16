package client.comproggui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EndGameResultController {

    @FXML
    private Label totalPointsLabel;

    @FXML
    private Label congratsOrNextTimeLabel;

    @FXML
    private Label winnerOrLoserLabel;


    public void setTotalPoints(int points) {
        totalPointsLabel.setText(String.valueOf(points));
    }


    public void setCongratsOrNextTimeText(String text) {
        congratsOrNextTimeLabel.setText(text);
    }

    public void setWinnerOrLoserText(String text) {
        winnerOrLoserLabel.setText(text);
    }


}
