package server.model;

import Utility.ClientCallback;
import com.mysql.cj.callback.UsernameCallback;
import server.controller.User;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Queue {
    private final Semaphore semaphore = new Semaphore(0); // Initially no permits
    private final List<String> joinedUsers = new ArrayList<>(); // List to store joined users
    public static boolean queueActive = false;

    private HashMap<ClientCallback, String> userCallbacks = new HashMap<>();
    private Timer timer;

    private static QueueTask queue;

    public HashMap<String, ClientCallback> activePlayers = new HashMap<>();

    public boolean isQueueActive() {
        return queueActive;
    }


    public void joinQueue(int queueTime, String userName, ClientCallback usernameCallback) {

        if (!queueActive) {
            queue = new QueueTask();
            queue.setTime(queueTime);
            queue.addToActiveUser(new User(userName, usernameCallback));
            queueActive = true;
            System.out.println("Queue is active for " + queueTime + " seconds.");
            timer = new Timer();
            timer.scheduleAtFixedRate(queue, 0, 1000);

        } else {

            queue.addToActiveUser(new User(userName, usernameCallback));
            System.out.println(userName + " joined the queue.");
        }

    }

    private void processJoinedUsers() {
        for (String user : joinedUsers) {
            System.out.println("Processing user: " + user);
        }
        joinedUsers.clear();
    }

    public void addToCallbackMaps(ClientCallback clientCallback, String id){
        userCallbacks.put(clientCallback, id);
    }

    public void addToActiveCallbackMaps(ClientCallback clientCallback, String id){
        activePlayers.put(id, clientCallback);
    }

    public HashMap<ClientCallback, String> getUserCallbacks() {
        return userCallbacks;
    }

    public static void setQueueActive(boolean queueActive) {
        Queue.queueActive = queueActive;
    }

    public void setUserCallbacks(HashMap<ClientCallback, String> userCallbacks) {
        this.userCallbacks = userCallbacks;
    }

}
