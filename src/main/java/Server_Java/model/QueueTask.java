package Server_Java.model;

import CORBA_IDL.Utility.ClientCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;

public class QueueTask extends TimerTask {

     int time = 11;

     int tempTimer = time;

    private static HashMap<String, ClientCallback> userCallbacks = new HashMap<>();

    public ArrayList<User> players = new ArrayList<>();

    public QueueTask() {
        tempTimer = time;
    }

    @Override
    public void run()  {
        time--;
        if (time == 0) {
            for (User temp: players){
                System.out.println("counting -> " + time);
                temp.getUserCallback().getQueueTime(time);
            }
            Queue.queueActive = false;

              // if (players.size() == 1) {// does not create
            //throw new GameStartException();
            //   } else {
            System.out.println("skibidi");
            ServerUtilityImpl.addGame(players);

            players = new ArrayList<>();
            time = tempTimer;
            //   }
            cancel();
        } else {


            System.out.println("active player size " + players.size());
            for (User temp: players){
                System.out.println("counting -> " + time);
                temp.getUserCallback().getQueueTime(time);
            }
        }
    }


    public void addToActiveUser(User player){players.add(player);}

    public void addToCallbackMaps(ClientCallback clientCallback, String userName){ userCallbacks.put(userName, clientCallback); }

    public HashMap<String, ClientCallback> getUserCallbacks() {
        return userCallbacks;
    }

    public void setUserCallbacks(HashMap<String, ClientCallback> userCallbacks) {
        this.userCallbacks = userCallbacks;
    }


    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}



