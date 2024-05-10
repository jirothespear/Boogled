package server.model;

import Utility.ClientCallback;
import com.mysql.cj.callback.UsernameCallback;

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

    private static final int queueTime = 0;

    private static QueueTask queue;

    public HashMap<String, ClientCallback> activePlayers = new HashMap<>();
    public List<String> getJoinedUsers() {
        return joinedUsers;
    }
    public boolean isQueueActive() {
        return queueActive;
    }


    public void joinQueue(int queueTime, String userName, ClientCallback usernameCallback) {
        queue = new QueueTask();


        if (!queueActive) {
            queue.addToCallbackMaps(usernameCallback, userName);
            queueActive = true;
            System.out.println("Queue is active for " + queueTime + " seconds.");
            timer = new Timer();
            queue = new QueueTask();
            timer.scheduleAtFixedRate(queue, 0, 1000);

        }

        queue.addToCallbackMaps(usernameCallback, userName);
        System.out.println(userName + " joined the queue.");
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

    //    public static void main(String[] args) {
//        System.out.println("Program is running!!!");
//        Queue queueSystem = new Queue();
//
//        Random random = new Random();
//        int queueTime = 10;
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        for (int i = 0; i < 5; i++) {
//            final String userName = "User" + (i + 1);
//            int delay = random.nextInt(17);
//            executor.execute(() -> {
//                try {
//                    Thread.sleep(delay * 1000);
//                    queueSystem.joinQueue(queueTime, userName);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//        executor.shutdown(); // Shutdown the executor after tasks are completed
//    }

}
