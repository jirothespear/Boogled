package server.model;

import Utility.ClientCallback;
import Utility.PlayerUtility;
import server.controller.ServerUtilityImpl;
import server.controller.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;

public class QueueTask extends TimerTask {

    static int time = 11;

    private static HashMap<String, ClientCallback> userCallbacks = new HashMap<>();

    public ArrayList<User> players = new ArrayList<>();

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
    }}

