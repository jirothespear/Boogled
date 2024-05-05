package server.model;

import Utility.ClientCallback;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Queue {
    private final Semaphore semaphore = new Semaphore(0); // Initially no permits
    private final List<String> joinedUsers = new ArrayList<>(); // List to store joined users
    private boolean queueActive = false;

    private HashMap<ClientCallback, Integer> userCallbacks;
    private Timer timer;

    public List<String> getJoinedUsers() {
        return joinedUsers;
    }
    public boolean isQueueActive() {
        return queueActive;
    }

    public void joinQueue(int queueTime, String userName) {
        if (!queueActive) {
            queueActive = true;
            System.out.println("Queue is active for " + queueTime + " seconds.");
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    queueActive = false;
                    System.out.println("Queue stopped. Processing joined users...");

                    processJoinedUsers();
                    timer.cancel();
                }
            }, queueTime * 1000);
        }

        joinedUsers.add(userName);
        semaphore.release();
        System.out.println(userName + " joined the queue.");
    }

    private void processJoinedUsers() {
        for (String user : joinedUsers) {
            System.out.println("Processing user: " + user);
        }
        joinedUsers.clear();
    }

    public void addToCallbackMaps(ClientCallback clientCallback, String id){

        userCallbacks.put(clientCallback, Integer.parseInt(id));
    }
    public HashMap<ClientCallback, Integer> getUserCallbacks() {
        return userCallbacks;
    }

    public void setUserCallbacks(HashMap<ClientCallback, Integer> userCallbacks) {
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
