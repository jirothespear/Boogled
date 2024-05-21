package Server_Java.model;

import java.util.ArrayList;
import CORBA_IDL.Utility.*;

import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class QueueTaskRunnable extends TimerTask {

     int time = 11;

     int tempTimer = time;

    private static ConcurrentHashMap<String, ClientCallback> userCallbacks = new ConcurrentHashMap<>();

    public ArrayList<User> players = new ArrayList<>();

    public QueueTaskRunnable() {
        tempTimer = time;
    }

    @Override
    public void run()  {



            time--;
            if (time == 0) {
                for (User temp : players) {
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

                cancel();
                //   }
            } else {

                for (User temp : players) {
                    System.out.println("counting -> " + time);
                    temp.getUserCallback().getQueueTime(time);
                }
            }


    }


    public void addToActiveUser(User player){players.add(player);}

    public void addToCallbackMaps(ClientCallback clientCallback, String userName){ userCallbacks.put(userName, clientCallback); }

    public ConcurrentHashMap<String, ClientCallback> getUserCallbacks() {
        return userCallbacks;
    }

    public void setUserCallbacks(ConcurrentHashMap<String, ClientCallback> userCallbacks) {
        this.userCallbacks = userCallbacks;
    }


    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}



