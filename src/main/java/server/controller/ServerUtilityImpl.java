
package server.controller;

import Utility.ClientCallback;
import Utility.GameStartException;
import Utility.LoginException;
import Utility.LogoutException;
import com.mysql.cj.xdevapi.Client;
import org.omg.CORBA.ORB;
import server.model.Game;
import server.model.Queue;
import sqlconnector.DataPB;

import javax.security.auth.callback.Callback;
import java.util.*;
import java.util.Map.Entry;

import java.util.HashMap;

public class ServerUtilityImpl extends Utility.PlayerUtilityPOA {
    private Game currentGame = new Game();
    private static HashMap<Integer, Game> activeGames = new HashMap<>();
    private Queue queueSystem = new Queue();

    private static int gameCount = 0;


    private String userName;

    private int queueTime = 20;
    private ORB orb;

    static private HashMap<String, ClientCallback> userCallbacks = new HashMap<>();

    @Override
    public void login(String username, String password) throws LoginException {
        String userNPasswd = username + "/" + password;
        if (DataPB.checkUser(userNPasswd)) {
            ClientCallback clientCallback = null;
            this.setUserName(username);
            userCallbacks.put(userNPasswd, clientCallback);
        } else throw new LoginException();


    }

    @Override
    public void userCallback(ClientCallback clientCallback, String username) {


        try {
            if (!userCallbacks.containsKey(clientCallback)) {
                userCallbacks.put(userName, clientCallback);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void logout(ClientCallback clientCallback, String username)  {

        if(userCallbacks.containsKey(clientCallback)){

            userCallbacks.remove(clientCallback);
        } //else throw new LogoutException();

    }


    @Override
    public void checkWord(String  playerAnswer, String userID, String gameID) {// Format "username/answer"

        if (activeGames.containsKey(Integer.parseInt(gameID))) {

            Game currentGame = activeGames.get(Integer.parseInt(gameID));

            String answer = playerAnswer;
            if (answer.length() >= 4 && DataPB.checkWord(answer)) {
                currentGame.getRound().addAnswerToPlayer(userID, answer);

            }


        }


    }

    @Override
    public String startGame(String user) {
        HashMap<ClientCallback, String> users;
        ArrayList<User> players = new ArrayList<>();

        // get users from queueSystem
        users = queueSystem.getUserCallbacks();

        for (Map.Entry<ClientCallback, String> entry : users.entrySet()) {
            players.add(new User(entry.getValue(), entry.getKey()));

        }


        if (!queueSystem.isQueueActive()) {

            //  if (players.size() == 1) {// does not create
            //throw new GameStartException();

            // } else {

            System.out.println("skibidi");
            Game game = new Game();
            game.start();
            game.startGame();
            gameCount++;
            game.setGameID(gameCount);
            activeGames.put(gameCount, game);

            return String.valueOf(gameCount);


        }


        return "null";
    }

    public static void addGame(ArrayList<User> players){
        gameCount++;
        Game game = new Game();
        game.startGame();
        game.setGameID(gameCount);
        activeGames.put(gameCount, game);

    }



    @Override
    public int showScore(String user, String gameID) {
        return 0;
    }

    @Override
    public void getQueueTime(String username) {
        queueSystem.addToCallbackMaps(userCallbacks.get(username), username);
        queueSystem.joinQueue(queueTime, userName, userCallbacks.get(username));

    }

    @Override
    public void getRoundTime(String username, String gameID) {
        activeGames.get(Integer.valueOf(gameID))
                .addToActivePlayers(new User(username, userCallbacks.get(username)));
    }

    @Override
    public String getLetterChoice(String gameID) {
        return null;
    }

    @Override
    public String getGameID(String username) {
        for (Map.Entry<Integer, Game> temp : activeGames.entrySet()){
            ArrayList<User> userArrayList = temp.getValue().getPlayers();
            for (User tempUser: userArrayList){
                if (tempUser.getUsername().equals(username))
                    return String.valueOf(temp.getKey());
            }
        }
        return "null";
    }

    public ORB getOrb() {
        return orb;
    }

    public void setOrb(ORB orb) {
        this.orb = orb;
    }

    public boolean checkIfChampionExist() {
        return !(currentGame.getChampion().getUsername().equalsIgnoreCase("null"));
    }


    public String showChampion() {
        return  currentGame.getChampion().getUsername();
    }


    public String getLeaderBoard() {
        return null;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public static HashMap<Integer, Game> getActiveGames() {
        return activeGames;
    }

    public static void setActiveGames(HashMap<Integer, Game> activeGames) {
        ServerUtilityImpl.activeGames = activeGames;
    }

    public Queue getQueueSystem() {
        return queueSystem;
    }

    public void setQueueSystem(Queue queueSystem) {
        this.queueSystem = queueSystem;
    }

    public static int getGameCount() {
        return gameCount;
    }

    public static void setGameCount(int gameCount) {
        ServerUtilityImpl.gameCount = gameCount;
    }

    public int getQueueTime() {
        return queueTime;
    }

    public void setQueueTime(int queueTime) {
        this.queueTime = queueTime;
    }

    public static HashMap<String, ClientCallback> getUserCallbacks() {
        return userCallbacks;
    }

    public static void setUserCallbacks(HashMap<String, ClientCallback> userCallbacks) {
        ServerUtilityImpl.userCallbacks = userCallbacks;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}



