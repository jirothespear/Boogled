package server.controller;

import Utility.ClientCallback;
import org.omg.CORBA.ORB;
import server.model.Game;
import server.model.Queue;
import sqlconnector.DataPB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ServerUtilityImpl extends Utility.ServerUtilityPOA {
    private Game currentGame = new Game();
    private HashMap<Integer, Game> activeGames = new HashMap<>();
    private Queue queueSystem = new Queue();
    private int gameCount = 0;
    private ORB orb;

    private HashMap<ClientCallback, String> userCallbacks;

    @Override
    public void login(String username, String password) {
        String userNPasswd = username + "/" + password;
        if (DataPB.checkUser(userNPasswd)) {
            ClientCallback clientCallback = null;
            userCallbacks.put(clientCallback, userNPasswd);
        }


    }

    @Override
    public void loginCallback(ClientCallback clientCallback) {

        if (!userCallbacks.containsKey(clientCallback)){
            userCallbacks.put(clientCallback, "user");
        }

    }

    @Override
    public void logout(ClientCallback clientCallback) {

        if(userCallbacks.containsKey(clientCallback)){

            userCallbacks.remove(clientCallback);
        }

    }

    @Override
    public boolean checkUser(String userNPasswd, String gameID) {
        return DataPB.checkUser(userNPasswd);
    }

    @Override
    public boolean checkWord(String  userNAnswer, String gameID) {// Format "username/answer"

        if (activeGames.containsKey(Integer.parseInt(gameID))) {

            Game currentGame = activeGames.get(Integer.parseInt(gameID));
            String [] input= userNAnswer.split("/");
            User user = currentGame.findUser(input[0]);
            String answer = input[1];
            if (answer.length() >= 4 && DataPB.checkWord(answer)) {
                currentGame.getRound().addAnswerToPlayer(user, answer);
                return true;// return parameter, answer is valid
            }
            return false;

        }

        return false;
    }

    @Override
    public void startGame(String user) {
        ArrayList<String>userNames = new ArrayList<>();
        ArrayList<User> players = new ArrayList<>();
        if(queueSystem.isQueueActive()){// queue is active

        }else {// start new queue
            //TO DO
        }
        // get users from queueSystem
        userNames = (ArrayList<String>) queueSystem.getJoinedUsers();
        for (int i = 0; i< userNames.size(); i++){
            players.add( new User(userNames.get(i)));
        }

        if(players.size() == 1){// does not create
            //TO DO
            // callback that there player count is insufficient
        }else {
            Game game = new Game();
            game.startGame(players);
            gameCount++;
            game.setGameID(gameCount);
            activeGames.put( gameCount, game);
            // callback for game is a go
        }

    }

    @Override
    public String showWinnerOfRound() {
        //Select the current game and set it to currentGame
        return currentGame.getWinnerOfRound();
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

    @Override
    public String getLetterChoice(String gameID) {// return the random 20 letters
        return activeGames.get(gameID).getRound().getLetters().toString();
    }




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



    public void startRound() {
        currentGame.newRound();
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

}
