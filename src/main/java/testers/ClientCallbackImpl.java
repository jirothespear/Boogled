package testers;

import Utility.ClientCallback;
import client.comproggui.WaitingRoomController;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;

public class ClientCallbackImpl extends Utility.ClientCallbackPOA {


    private WaitingRoomController waitingRoomController;

    ORB orb;
    @Override
    public String startRound() {
        return null;
    }

    @Override
    public void roundEnd() {

    }

    @Override
    public void gameFinish() {

    }

    @Override
    public int getScore(int time) {
        return 0;
    }

    @Override
    public int getRoundTime(int time) {
        return 0;
    }

    @Override
    public int getQueueTime(int time) {
        System.out.println(time+" ++");


        if (waitingRoomController != null) {
            System.out.println("Setting queue time");
            waitingRoomController.setQueueTime(time);
        }
        return time;
    }

    @Override
    public int getGameTime(int time) {
        return 0;
    }

    @Override
    public String getLetterChoice(String letters) {

        System.out.println(letters.toString());
        return letters;
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

}
