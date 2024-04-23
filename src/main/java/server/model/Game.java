package server.model;

import server.controller.User;
import sqlconnector.DataPB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {
    /*
    following variable does not changer every round
     */
    private static ArrayList<User>  players = new ArrayList<>();// list of players
    private static HashMap<User, Integer> overallPoints = new HashMap<>();// stores points
    private static HashMap<User, Integer> playerPlacing = new HashMap<>(); // stores the number of wins per player
    private static User champion = new User();
    /*
    following variables changes every round
     */
    private static HashMap<String, Integer> allAnswers = new HashMap<>();
    private static HashMap<User, Integer> pointsPerRound = new HashMap<>();// resets value every round
    private static HashMap<User,ArrayList<String>> answersOfPlayers = new HashMap<>();
    /*
    this variable are optional remove if needed
     */
    private static Boolean isOpen;// status for game if waiting or not
    private static int roundCount =0;// count for monitoring what round is taking place

    public static User getChampion(){
        return champion;
    }


    /**
     * Method for checking status for game
     * @return true if still waiting
     */
    public Boolean getOpen() {
        return isOpen;
    }

    public Game(String hostPlayer){
        DataPB.setCon();
        User hostUser = DataPB.getUser(hostPlayer);
        players.add(hostUser);
        isOpen = true;
    }
    public void enterGame(String anotherPlayer){
        User hostUser = DataPB.getUser(anotherPlayer);
        players.add(hostUser);
    }
    public static  void startGame(ArrayList<User> plrs){
        System.out.println("Game is starting");
        players = plrs;
        isOpen= false;
        allAnswers = new HashMap<>();
        for (int i =0; i < players.size(); i++){
            overallPoints.put(players.get(i),0);
            pointsPerRound.put(players.get(i),0);
            playerPlacing.put(players.get(i),0);
            answersOfPlayers.put(players.get(i), new ArrayList<>());
        }
    }
    public static void newRound(){
        roundCount++;
        System.out.println("Round "+roundCount +"!");
        //Reset list containing all answers
        allAnswers = new HashMap<>();
        // Reset points to 0 for each user
        pointsPerRound.forEach((user, points) -> pointsPerRound.put(user, 0));
        // Clear answers for each user
        answersOfPlayers.forEach((user, list) -> list.clear());
        //execute round and timer

        //the following methods when after timer of this round is finished
        filterAnswers();
        countScores();
        User winner = findWinnerOfRound();
        System.out.println("Winner of round "+roundCount+" is "+winner.getUsername());

       if(checkWinner()){
           System.out.println("The winner of this game is : "+champion.getUsername());
           updateUserDB();// updates user table in DB
           //ends game
       }


    }
    /*
    finds a user using the username
    return null if username does not exist in list
     */
    public static User findUser(String username){
        for (int i =0; i < players.size(); i++){
            if(players.get(i).getUsername().equalsIgnoreCase(username)){
                return players.get(i);
            }
        }
        System.out.println("User not found in game");
        return null;
    }
    /*
    method adds the valid answer to the list of the corresponding user
     */
    public static void addAnswerToPlayer(User user,String answer){
       addToAllAnswersList(answer);
        for (Map.Entry<User, ArrayList<String>> entry : answersOfPlayers.entrySet()) {
            if (entry.getKey().getUsername().equalsIgnoreCase(user.getUsername())){
                entry.getValue().add(answer);
            }
        }
    }
    /*
    filter answers of players
     */
    public static void filterAnswers(){
        for (Map.Entry<User, ArrayList<String>> entry : answersOfPlayers.entrySet()) {// loop per user
            ArrayList<String> userAnswers = entry.getValue();
            for (int i = 0; i < userAnswers.size(); i++) {// loop for answers of user
                for (Map.Entry<String, Integer> ety : allAnswers.entrySet()){// loop for all answers
                    if(ety.getKey().equalsIgnoreCase(userAnswers.get(i))){
                        if(ety.getValue()>1){
                            // remove
                            userAnswers.remove(i);
                        }
                    }
                }
            }
        }
    }
    public static void countScores(){
        for (Map.Entry<User, ArrayList<String>> entry : answersOfPlayers.entrySet()) {
            int score = entry.getValue().stream()
                    .mapToInt(String::length)
                    .sum();

            User user = entry.getKey();
            pointsPerRound.put(user, score);
        }
    }
    public static User findWinnerOfRound(){
        User winner = null;
        int highestScore = 0;

        for (Map.Entry<User, Integer> entry : pointsPerRound.entrySet()) {
            if (entry.getValue() > highestScore) {
                highestScore = entry.getValue();
                winner = entry.getKey();
            }
        }
        //Add score of winner to overallPoint for corresponding
        for (Map.Entry<User, Integer> entry : overallPoints.entrySet()){
            if(entry.getKey().getUsername().equalsIgnoreCase(winner.getUsername())){
                entry.setValue(entry.getValue() + highestScore);
            }
        }
        for (Map.Entry<User, Integer> entry : playerPlacing.entrySet()){
            if(entry.getKey().getUsername().equalsIgnoreCase(winner.getUsername())){
                entry.setValue(entry.getValue()+1);
            }
        }

        return winner;

    }
    public static boolean checkWinner(){
        for (Map.Entry<User, Integer> entry : playerPlacing.entrySet()){
            if(entry.getValue() == 3){
                System.out.println(entry.getKey().getUsername() + " is the winner");
                champion = entry.getKey();// set winner of the game
                return true;
            }
        }
        return false;
    }
    public static void updateUserDB(){
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

    /*
    Adds an answer to allAnswer list
    this method also makes sure that no answer is duplicated
     */
    private static void addToAllAnswersList(String answer){
        if(!(allAnswers.isEmpty())){
            for (Map.Entry<String, Integer> entry : allAnswers.entrySet()){
                if(entry.getKey().equalsIgnoreCase(answer)){
                    entry.setValue(entry.getValue()+1);
                    return;
                }
            }
        }
        allAnswers.put(answer, 1);



    }
}
