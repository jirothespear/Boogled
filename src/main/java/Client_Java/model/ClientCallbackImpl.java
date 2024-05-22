package Client_Java.model;

import Client_Java.controllers.*;

import org.omg.CORBA.ORB;

public class ClientCallbackImpl extends CORBA_IDL.Utility.ClientCallbackPOA {

    // Controllers
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
        boolean isWinner = winner.equals(gameRoomController.getCurrentGameUser());
        gameRoomController.setWinner(isWinner);

        System.out.println("ROUND END SETTING");

        System.out.println("CLIENT CB CURRENT: " +gameRoomController.getCurrentGameUser()  + "USER WIN: "+ winner + "Points: " + points);
        if (endRoundResultController != null) {
            System.out.println("End Round Result Not Null");

            System.out.println(isWinner);
            endRoundResultController.setPoints(points);
            endRoundResultController.setResult(isWinner, points);
        }

    }

    @Override
    public void gameFinish(String winner, String points) {
        System.out.println("GAME FINISH SETTING");


        if (gameResultController != null) {

            gameResultController.setTotalPoints(Integer.parseInt(points));
            gameResultController.setWinnerOrLoserText(winner.equals(gameRoomController.getCurrentGameUser()) ? "You won!" : "You lost!");
            gameResultController.setCongratsOrNextTimeText(winner.equals(gameRoomController.getCurrentGameUser()) ? "Congratulations!" : "Better luck next time!");


        }
    }

    @Override
    public int getScore(int time) {

        return 0;
    }

    @Override
    public void getRoundTime(int time) {
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
//
    @Override
    public void getChampion(String username, String points) {
        if (gameRoomController != null){
            System.out.println("Setting champion");
            System.out.println("Game Room Not Null");
            gameRoomController.setGameChampion(username, points);
        }

    }

    public void setORB (ORB orb){
        this.orb = orb;
    }

    public void setWaitingRoomController(WaitingRoomController waitingRoomController) {
        this.waitingRoomController = waitingRoomController;
    }

    public void setGameRoomController(GameRoomController gameRoomController) {
        this.gameRoomController = gameRoomController;
    }



    public void setGameResultController(EndGameResultController gameResultController) {
        this.gameResultController = gameResultController;
    }


    public void setEndRoundResultController(EndRoundResultController endRoundResultController) {
        this.endRoundResultController = endRoundResultController;
    }

}
