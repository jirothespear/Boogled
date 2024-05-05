package server.model;

import Utility.ClientCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

public class QueueTask extends TimerTask {

    static int time = 0;

    private HashMap<ClientCallback, String> userCallbacks;

    @Override
    public void run() {
        time++;
        for (Map.Entry<ClientCallback, String> entry: userCallbacks.entrySet()){
            entry.getKey().getQueueTime(time);
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
