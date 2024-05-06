package server.model;

import Utility.ClientCallback;
import Utility.ServerUtility;
import server.controller.ServerUtilityImpl;
import server.controller.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

public class QueueTask extends TimerTask {

    static int time = 0;

    private static HashMap<ClientCallback, String> userCallbacks = new HashMap<>();

    public ArrayList<User> players = new ArrayList<>();

    @Override
    public void run() {
        time++;

        if (time == 10){
            for (Map.Entry<ClientCallback, String> entry : userCallbacks.entrySet()) {
                System.out.println("counting -> " + time);
                entry.getKey().getQueueTime(time);
            }
            Queue.queueActive = false;

            for (Map.Entry<ClientCallback, String> entry : userCallbacks.entrySet()) {
                players.add(new User(entry.getValue(), entry.getKey()));
            }
                if (players.size() == 1) {// does not create
                //throw new GameStartException();
                } else {
                System.out.println("skibidi");
                ServerUtilityImpl.addGame(players);
            }

                cancel();
        } else {
            for (Map.Entry<ClientCallback, String> entry : userCallbacks.entrySet()) {
                System.out.println("counting -> " + time);
                entry.getKey().getQueueTime(time);
            }
        }


    }

    public void addToCallbackMaps(ClientCallback clientCallback, String id){

        userCallbacks.put(clientCallback, id);
    }

    public HashMap<ClientCallback, String> getUserCallbacks() {
        return userCallbacks;
    }

    public void setUserCallbacks(HashMap<ClientCallback, String> userCallbacks) {
        this.userCallbacks = userCallbacks;
    }
}
