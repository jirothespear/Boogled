package testers;

import Utility.ClientCallback;
import client.comproggui.GameRoomController;
import client.comproggui.WaitingRoomController;
import javafx.application.Platform;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;

public class ClientCallbackImpl extends Utility.ClientCallbackPOA {


    private WaitingRoomController waitingRoomController;

    private GameRoomController gameRoomController;



    ORB orb;
    @Override
    public String startRound() {
        return null;
    }

    @Override
    public void roundEnd(String winner, int points) {

    }

    @Override
    public void gameFinish(String winner, String points) {

    }

    @Override
    public int getScore(int time) {
        return 0;
    }

    @Override
    public void getRoundTime(int time) {
        System.out.println("Setting round time");
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

}
