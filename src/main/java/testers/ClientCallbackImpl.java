package testers;

import Utility.ClientCallback;
import client.comproggui.*;
import javafx.application.Platform;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;

public class ClientCallbackImpl extends Utility.ClientCallbackPOA {


    private WaitingRoomController waitingRoomController;

    private GameRoomController gameRoomController;

    private EndGameResultController gameResultController;

    private EndRoundResultController endRoundResultController;


    private GameFinishedController gameFinishedController;

    private RoundStackPaneController roundStackPaneController;


    ORB orb;
    @Override
    public String startRound() {
        return null;
    }

    @Override
    public void roundEnd(String winner, int points) {
        System.out.println("ROUND END SETTING");
        if (endRoundResultController != null) {
            boolean isWinner = winner.equals(gameRoomController.getCurrentGameUser());
            endRoundResultController.setResult(isWinner, points);
            Platform.runLater(() -> gameRoomController.onRoundFinished());
        }

    }

    @Override
    public void gameFinish(String winner, String points) {
        System.out.println("GAME FINISH SETTING");

        // Check if the gameResultController is not null
        if (gameResultController != null) {

            gameResultController.setTotalPoints(Integer.parseInt(points));
            gameResultController.setWinnerOrLoserText(winner.equals(gameRoomController.getCurrentGameUser()) ? "You won!" : "You lost!");
            gameResultController.setCongratsOrNextTimeText(winner.equals(gameRoomController.getCurrentGameUser()) ? "Congratulations!" : "Better luck next time!");

            Platform.runLater(() -> {

                gameRoomController.onGameFinished();
            });
        }
    }

    @Override
    public int getScore(int time) {
        return 0;
    }

    @Override
    public void getRoundTime(int time) {
//        System.out.println("Setting round time");
        if (gameRoomController != null){
            gameRoomController.setRoundTime(time);
        }

    }

    @Override
    public void getQueueTime(int time) {
        System.out.println("Setting queue time");
        if (waitingRoomController != null){
            waitingRoomController.setQueueTime(time);
        }

    }


    @Override
    public void getLetterChoice(String letters) {

        System.out.println("Oh shocks " + letters.toString());
        if (gameRoomController != null){
            gameRoomController.setGameLetterChoice(letters);
        }
    }

    public void setORB (ORB orb){
        this.orb = orb;
    }

    public WaitingRoomController getWaitingRoomController() {
        return waitingRoomController;
    }

    public void setWaitingRoomController(WaitingRoomController waitingRoomController) {
        this.waitingRoomController = waitingRoomController;
    }

    public GameRoomController getGameRoomController() {
        return gameRoomController;
    }

    public void setGameRoomController(GameRoomController gameRoomController) {
        this.gameRoomController = gameRoomController;
    }



    public EndGameResultController getGameResultController() {
        return gameResultController;
    }

    public void setGameResultController(EndGameResultController gameResultController) {
        this.gameResultController = gameResultController;
    }

    public EndRoundResultController getEndRoundResultController() {
        return endRoundResultController;
    }

    public void setEndRoundResultController(EndRoundResultController endRoundResultController) {
        this.endRoundResultController = endRoundResultController;
    }

    public GameFinishedController getGameFinishedController() {
        return gameFinishedController;
    }

    public void setGameFinishedController(GameFinishedController gameFinishedController) {
        this.gameFinishedController = gameFinishedController;
    }

    public RoundStackPaneController getRoundStackPaneController() {
        return roundStackPaneController;
    }

    public void setRoundStackPaneController(RoundStackPaneController roundStackPaneController) {
        this.roundStackPaneController = roundStackPaneController;
    }
}
