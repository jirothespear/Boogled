package server.model;

import Utility.ClientCallback;
import server.controller.User;
import sqlconnector.DataPB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class Game extends Thread {
    /*
    following variable does not changer every round
     */
    private  ArrayList<User>  players = new ArrayList<>();// list of players
    private  HashMap<User, Integer> overallPoints = new HashMap<>();// stores points
    private  HashMap<User, Integer> playerPlacing = new HashMap<>(); // stores the number of wins per player
    private  User champion = new User();
    private int gameID=0;// use to identify each game from another

    private HashMap<ClientCallback, String> userCallbacks;



    /*
    Following variabls are used in rounds
     */
    private int scoreOfRoundWinner;
    private String winnerOfRound;
    private int roundCount=0;
    private Round round;

    private Boolean roundWin = false;
    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
    public ArrayList<User> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<User> players) {
        this.players = players;
    }

    public HashMap<User, Integer> getOverallPoints() {
        return overallPoints;
    }

    public void setOverallPoints(HashMap<User, Integer> overallPoints) {
        this.overallPoints = overallPoints;
    }

    public HashMap<User, Integer> getPlayerPlacing() {
        return playerPlacing;
    }

    public void setPlayerPlacing(HashMap<User, Integer> playerPlacing) {
        this.playerPlacing = playerPlacing;
    }

    public User getChampion() {
        return champion;
    }

    public void setChampion(User champion) {
        this.champion = champion;
    }

    public int getScoreOfRoundWinner() {
        return scoreOfRoundWinner;
    }

    public void setScoreOfRoundWinner(int scoreOfRoundWinner) {
        this.scoreOfRoundWinner = scoreOfRoundWinner;
    }

    public String getWinnerOfRound() {
        return winnerOfRound;
    }

    public void setWinnerOfRound(String winnerOfRound) {
        this.winnerOfRound = winnerOfRound;
    }

    public HashMap<ClientCallback, String> getUserCallbacks() {
        return userCallbacks;
    }

    public void setUserCallbacks(HashMap<ClientCallback, String> userCallbacks) {
        this.userCallbacks = userCallbacks;
    }


    public Game(){
        DataPB.setCon();
    }
    public  void startGame(){// sets up variables for game
        System.out.println("Game is starting");
        roundCount = 0;
        for (int i =0; i < players.size(); i++){
            overallPoints.put(players.get(i),0);
            playerPlacing.put(players.get(i),0);
        }

        if (!roundWin) {
            round = new Round(players);
            round.setGame(this);
            roundCount++;
            scoreOfRoundWinner =0;
            winnerOfRound =" ";
            round.newRound(roundCount);
            round.setRoundCount(roundCount);
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(round, 0, 1000);

        } else {
            for (User temp: players){
                temp.getUserCallback().gameFinish(winnerOfRound, String.valueOf(scoreOfRoundWinner));
            }

        }

    }

    public void checkWinner (String [] winnerOfCurrentRound){


        if(!winnerOfCurrentRound[0].equals("null")) {

            String winnerUsername = winnerOfCurrentRound[0];
            String winnerScore = winnerOfCurrentRound[1];
            updateMaps(findUser(winnerUsername), Integer.parseInt(winnerScore));

            scoreOfRoundWinner = Integer.parseInt(winnerScore);
            winnerOfRound = winnerUsername;

            if (champion.getUsername().equalsIgnoreCase("null")) {// ends the loop when there is a winner
                updateUserDB();// updates user table in DB
                roundWin = true;
            }

        }


    }

    /*
    finds a user using the username
    return null if username does not exist in list
     */
    public  User findUser(String username){
        for (int i =0; i < players.size(); i++){
            if(players.get(i).getUsername().equalsIgnoreCase(username)){
                return players.get(i);
            }
        }
        System.out.println("User not found in game");
        return null;
    }

    public  void updateMaps( User winnerOfRound, int scoreOfWinner){
        User winner = winnerOfRound;
        int highestScore = scoreOfWinner;

        //Add score of winner to overallPoint for corresponding
        for (Map.Entry<User, Integer> entry : overallPoints.entrySet()){
            if(entry.getKey().getUsername().equalsIgnoreCase(winner.getUsername())){
                entry.setValue(entry.getValue() + highestScore);
            }
        }
        for (Map.Entry<User, Integer> entry : playerPlacing.entrySet()){
            if(entry.getKey().getUsername().equalsIgnoreCase(winner.getUsername())){
                entry.setValue(entry.getValue()+1);
                if(entry.getValue() == 3){
                    System.out.println(winner.getUsername() + " is the winner fo the game");
                    champion = winner;
                }
            }
        }

    }

    public void updateUserDB(){// changes user's score if current high score is higher or lower that new high score
        String championUsername= champion.getUsername();
        int currentScore = DataPB.getScoreOfUser(championUsername);
        for (Map.Entry<User, Integer> entry : overallPoints.entrySet()){
            if(entry.getKey().getUsername().equalsIgnoreCase(championUsername)){
                if(entry.getValue() > currentScore){
                    DataPB.setScore(championUsername,entry.getValue());
                }
            }
        }

    }

    public void addToActivePlayers (User player){

        players.add(player);
    }


}
