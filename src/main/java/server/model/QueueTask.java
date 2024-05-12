package server.model;

import Utility.ClientCallback;
import Utility.PlayerUtility;
import server.controller.ServerUtilityImpl;
import server.controller.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class QueueTask extends TimerTask {

    static int time = 11;

    private static HashMap<String, ClientCallback> userCallbacks = new HashMap<>();

    public ArrayList<User> players = new ArrayList<>();

    @Override
    public void run() {
        time--;
        if (time == 0  ) {
            for (Map.Entry<String, ClientCallback> entry : userCallbacks.entrySet()) {
                System.out.println("counting -> " + time);
                entry.getValue().getQueueTime(time);
            }
            Queue.queueActive = false;

            for (Map.Entry<String, ClientCallback> entry : userCallbacks.entrySet()) {
                System.out.println("Player in callback: " + entry.getKey());
                players.add(new User(entry.getKey(), entry.getValue()));
            }
            //   if (players.size() == 1) {// does not create
            //throw new GameStartException();
            //   } else {
            System.out.println("skibidi");
            ServerUtilityImpl.addGame(players);;
            //   }

            cancel();
        } else {
            ExecutorService executorService = Executors.newFixedThreadPool(5);
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {

                        for (Map.Entry<String, ClientCallback> entry : userCallbacks.entrySet()) {
                            System.out.println("counting -> " + time);


                            entry.getValue().getQueueTime(time);

                        }
                    }
                });



        }


    }

    public void addToCallbackMaps(ClientCallback clientCallback, String userName){

        userCallbacks.put(userName, clientCallback);
    }

    public HashMap<String, ClientCallback> getUserCallbacks() {
        return userCallbacks;
    }

    public void setUserCallbacks(HashMap<String, ClientCallback> userCallbacks) {
        this.userCallbacks = userCallbacks;
    }

}

