package client.comproggui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import testers.ClientCallbackImpl;

public class EndGameResultController {

    @FXML
    private Label totalPointsLabel;

    @FXML
    private Label congratsOrNextTimeLabel;

    @FXML
    private Label winnerOrLoserLabel;

    private ClientCallbackImpl clientCallbackImpl;

    public Label getTotalPointsLabel() {
        return totalPointsLabel;
    }

    public void setTotalPointsLabel(Label totalPointsLabel) {
        this.totalPointsLabel = totalPointsLabel;
    }

    public Label getCongratsOrNextTimeLabel() {
        return congratsOrNextTimeLabel;
    }

    public void setCongratsOrNextTimeLabel(String congratsOrNextTimeLabel) {
        this.congratsOrNextTimeLabel.setText(congratsOrNextTimeLabel);
    }

    public Label getWinnerOrLoserLabel() {
        return winnerOrLoserLabel;
    }

    public void setWinnerOrLoserLabel(String winnerOrLoserLabel) {
        this.winnerOrLoserLabel.setText( winnerOrLoserLabel);
    }

    public ClientCallbackImpl getClientCallbackImpl() {
        return clientCallbackImpl;
    }

    public void setClientCallbackImpl(ClientCallbackImpl clientCallbackImpl) {
        this.clientCallbackImpl = clientCallbackImpl;
    }

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
