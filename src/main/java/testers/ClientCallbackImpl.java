package testers;

import Utility.ClientCallback;
import client.comproggui.WaitingRoomController;
import javafx.application.Platform;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;

public class ClientCallbackImpl extends Utility.ClientCallbackPOA {

//    public ClientCallbackImpl(WaitingRoomController waitingRoomController) {
//        this.waitingRoomController = waitingRoomController;
//    }


    private WaitingRoomController waitingRoomController;

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

        System.out.println(letters.toString());
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
