
package Server_Java.model;

import CORBA_IDL.Utility.;
import org.omg.CORBA.ORB;
import Server_Java.sqlconnector.DataPB;

import java.util.*;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ServerUtilityImpl extends CORBA_IDL.Utility.PlayerUtilityPOA {


    private Game currentGame = new Game();
    private static ConcurrentHashMap<Integer, Game> activeGames = new ConcurrentHashMap<>();
    private Queue queueSystem = new Queue();

    private static int gameCount = 0;



    private ORB orb;

    static private ConcurrentHashMap<String, ClientCallback> userCallbacks = new ConcurrentHashMap<>();

    private ArrayList<User> playerCallbacks = new ArrayList<>();

    @Override
    public void login(String username, String password) throws LoginException {
        String userNPasswd = username + "/" + password;
        if (DataPB.checkUser(userNPasswd)) {

        } else throw new LoginException();
    }

    @Override
    public void userCallback(ClientCallback clientCallback, String userName) {

        try {
            if (!userCallbacks.containsKey(userName)) {
                userCallbacks.put(userName, clientCallback);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("size!!");
        System.out.println(userCallbacks.size());
    }

    @Override
    public void logout(ClientCallback clientCallback, String username)  {
        userCallbacks.remove(username);
    }


    @Override
    public void checkWord(String  playerAnswer, String userID, String gameID) throws InvalidWordException {// Format "username/answer"
        System.out.println("game: " + gameID  + " user: " + userID + " answer: " + playerAnswer );
        if (activeGames.containsKey(Integer.parseInt(gameID))) {
            Game currentGame = activeGames.get(Integer.parseInt(gameID));
            String answer = playerAnswer;
            if (answer.length() >= 4 && DataPB.checkWord(answer)) {
                currentGame.getRound().addAnswerToPlayer(userID, answer);
            } else
                throw new InvalidWordException();
        }
    }

    @Override
    public String startGame(String user) {
        ConcurrentHashMap<ClientCallback, String> users;
        ArrayList<User> players = new ArrayList<>();
        users = queueSystem.getUserCallbacks();

        for (Map.Entry<ClientCallback, String> entry : users.entrySet()) {
            players.add(new User(entry.getValue(), entry.getKey()));
        }

        if (!queueSystem.isQueueActive()) {
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
        Game.roundTime = DataPB.getRoundTime();
        game.start();
        game.setPlayers(players);
        game.startGame();
        game.setGameID(gameCount);
        activeGames.put(gameCount, game);
    }

    public User getUser(String username){
        for (Map.Entry<String, ClientCallback> entry : userCallbacks.entrySet()) {
            if (entry.getKey().equals(username)){
                return new User(entry.getKey(), entry.getValue());
            }
        }
        return null;
    }

    @Override
    public int showScore(String user, String gameID) {
        System.out.println("GameID: " + gameID + " User: " + user);
        int gameIDInt = Integer.parseInt(gameID);
       // System.out.println("Score: " + activeGames.get(gameIDInt).getRound().getRoundPoint());
        ArrayList<UserScore> roundPoint = activeGames.get(gameIDInt).getPointsPrevious();

        System.out.println("points skibidi -> " + roundPoint.size());
        for(UserScore temp: roundPoint){
            if (temp.getUsername().equals(user)){
                return temp.getScore();
            }
        }
        return 0;
    }

    @Override
    public void getQueueTime(String userName) {
        queueTime = DataPB.getQueueTime();
        System.out.println();
        queueSystem.joinQueue(queueTime, userName, userCallbacks.get(userName));
        System.out.println("Queue time for " + userName+  "   " +userCallbacks.get(userName));
    }

    @Override
    public void getRoundTime(String username, String gameID) {
        System.out.println("Round time for " + username + " in game " + gameID);
        activeGames.get(Integer.valueOf(gameID))
                .addToActivePlayers(new User(username, userCallbacks.get(username)));
    }

    @Override
    public String getLetterChoice(String gameID) {
        return activeGames.get(Integer.valueOf(gameID)).getRound().getLetters();
    }

    @Override
    public String getGameID(String username) {
        System.out.println("Active Games: "+ activeGames.size());
        for (Map.Entry<Integer, Game> temp : activeGames.entrySet()){
            ArrayList<User> userArrayList = temp.getValue().getPlayers();
            for (User tempUser: userArrayList){
                if (tempUser.getUsername().equals(username))
                    return String.valueOf(temp.getKey());
            }
        }
        return "null";
    }
//
    @Override
    public String getRoundCount(String gameID) {
        return String.valueOf(activeGames.get(Integer.valueOf(gameID)).getRound().getRoundCount());
    }

    @Override
    public String[] getLeaderboardUsernames() {
        return DataPB.getLeaderboardUsernames();
    }

    @Override
    public int[] getLeaderboardPoints() {
        return DataPB.getLeaderboardPoints();
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

    public static ConcurrentHashMap<Integer, Game> getActiveGames() {
        return activeGames;
    }

    public static void setActiveGames(ConcurrentHashMap<Integer, Game> activeGames) {
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

    public static int queueTime = 0;

    public static HashMap<String, ClientCallback> getUserCallbacks() {
        return userCallbacks;
    }

}



