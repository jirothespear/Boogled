
package server.controller;

import Utility.ClientCallback;
import Utility.GameStartException;
import Utility.LoginException;
import Utility.LogoutException;
import org.omg.CORBA.ORB;
import server.model.Game;
import server.model.Queue;
import sqlconnector.DataPB;

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

    static private HashMap<ClientCallback, String> userCallbacks = new HashMap<>();

    @Override
    public void login(String username, String password) throws LoginException {
        String userNPasswd = username + "/" + password;
        if (DataPB.checkUser(userNPasswd)) {
            ClientCallback clientCallback = null;
            this.setUserName(username);
            userCallbacks.put(clientCallback, userNPasswd);
        } else throw new LoginException();


    }

    @Override
    public void userCallback(ClientCallback clientCallback, String username) {


        try {
            if (!userCallbacks.containsKey(clientCallback)) {
                userCallbacks.put(clientCallback, userName);
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
        if (queueSystem.isQueueActive()) {// queue is active
            for (Entry<ClientCallback, String> entry : userCallbacks.entrySet()) {
                if (Objects.equals(entry.getValue(), user)) {
                    queueSystem.addToCallbackMaps(entry.getKey(), user);
                    queueSystem.joinQueue(queueTime, user, entry.getKey());
                    break;
                }
            }


        } else {// start new queue
            for (Entry<ClientCallback, String> entry : userCallbacks.entrySet()) {
                if (Objects.equals(entry.getValue(), user)) {
                    queueSystem.addToCallbackMaps(entry.getKey(), user);
                    queueSystem.joinQueue(queueTime, user, entry.getKey());
                    break;
                }
            }

        }
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
            game.startGame(players);
            gameCount++;
            game.setGameID(gameCount);
            activeGames.put(gameCount, game);
            //return String.valueOf(game.getGameID());
            // callback for game is a go
            //    }

            //return null;

            return String.valueOf(gameCount);


        }


        return "null";
    }

    public static void addGame(ArrayList<User> players){
        gameCount++;
        Game game = new Game();
        game.startGame(players);
        game.setGameID(gameCount);
        activeGames.put(gameCount, game);

    }



    @Override
    public int showScore(String user, String gameID) {
        return 0;
    }

    /*
    @Override
    public String getLetterChoice() {// return the random 20 letters
        StringBuilder result = new StringBuilder();
        StringBuilder consonantsBuilder = new StringBuilder();
        StringBuilder vowelsBuilder = new StringBuilder();
        Random random = new Random();
        String consonants = "bcdfghjklmnpqrstvwxyz"; // Consonants
        String vowels = "aeiou"; // Vowels

        // Generate 13 consonants
        for (int i = 0; i < 13; i++) {
            int index = random.nextInt(consonants.length());
            consonantsBuilder.append(consonants.charAt(index));
        }

        // Generate 7 vowels
        for (int i = 0; i < 7; i++) {
            int index = random.nextInt(vowels.length());
            vowelsBuilder.append(vowels.charAt(index));
        }

        // Shuffle the consonants and vowels separately
        shuffleStringBuilder(consonantsBuilder, random);
        shuffleStringBuilder(vowelsBuilder, random);

        // Append consonants and vowels to the result
        result.append(consonantsBuilder).append("\n.").append(vowelsBuilder);

        return result.toString();
    }

     */


    public void joinGame(String user, String gameID) {

    }

    // Method to shuffle a StringBuilder
    private static void shuffleStringBuilder(StringBuilder sb, Random random) {
        for (int i = sb.length() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(j));
            sb.setCharAt(j, temp);
        }
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

    public static HashMap<ClientCallback, String> getUserCallbacks() {
        return userCallbacks;
    }

    public static void setUserCallbacks(HashMap<ClientCallback, String> userCallbacks) {
        ServerUtilityImpl.userCallbacks = userCallbacks;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}



