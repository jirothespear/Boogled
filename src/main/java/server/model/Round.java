package server.model;

import server.controller.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Round {
    /*
    following variables changes every newRound
     */
    private static HashMap<String, Integer> allAnswers = new HashMap<>();
    private static HashMap<User, Integer> pointsPerRound = new HashMap<>();// resets value every round
    private static HashMap<User, ArrayList<String>> answersOfPlayers = new HashMap<>();

    private static  int timerCount;
    /**
     * Constructor
     * @param players list of players to be used for Maps
     */
    public Round (ArrayList<User>  players){
        allAnswers = new HashMap<>();
        for (int i =0; i < players.size(); i++){
            pointsPerRound.put(players.get(i),0);
            answersOfPlayers.put(players.get(i), new ArrayList<>());
        }
    }

    public static int getTimerCount() {
        return timerCount;
    }

    public static void setTimerCount(int timerCount) {
        Round.timerCount = timerCount;
    }

    /**
     * Starts a new round
     * @param roundCount round count to monitor what round is executing
     */

    public static void newRound (int roundCount){
        System.out.println("Round "+roundCount +"!");
        //Reset list containing all answers
        allAnswers = new HashMap<>();
        // Reset points to 0 for each user
        pointsPerRound.forEach((user, points) -> pointsPerRound.put(user, 0));
        // Clear answers for each user
        answersOfPlayers.forEach((user, list) -> list.clear());
        while (true){// end when timer is done
            if(false){ // TO DO.
                break;
            }
        }
        //the following methods are executed after the timer of round is finished
        filterAnswers();
        countScoresPlayers();

        String winnerOfCurrentRound[] = updateRoundWinner().split("/");//Format username/highscore
        String winnerUsername= winnerOfCurrentRound[0];
        String winnerScore= winnerOfCurrentRound[1];
        System.out.println("Winner for round "+ roundCount+" is"+ winnerUsername
        +" with a score of "+ winnerScore);
    }

    /**
     * method adds the valid answer to the list of the corresponding user
     * @param user source of answer
     * @param answer input answer
     */
    public static void addAnswerToPlayer(User user,String answer){
        addToAllAnswersList(answer);
        for (Map.Entry<User, ArrayList<String>> entry : answersOfPlayers.entrySet()) {
            if (entry.getKey().getUsername().equalsIgnoreCase(user.getUsername())){
                entry.getValue().add(answer);
            }
        }
    }

    /**
     *  Adds an answer to allAnswer list
     *  this method also counts the number of times an answer is repeated
     *  allAnswers.map will help aid in filtering list of answers per player
     * @param answer inputed answer
     */
    private static void addToAllAnswersList(String answer){
        if(!(allAnswers.isEmpty())){
            for (Map.Entry<String, Integer> entry : allAnswers.entrySet()){
                if(entry.getKey().equalsIgnoreCase(answer)){
                    entry.setValue(entry.getValue()+1);
                    return;
                }else {
                    allAnswers.put(answer, 1);
                }
            }
        }else {
            allAnswers.put(answer, 1);
        }

    }
    /**
     * filter answers of players
     * removes all the repeated answers from all list of players
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
                            i--;
                        }
                    }
                }
            }
            answersOfPlayers.put(entry.getKey(), userAnswers);
        }
    }

    /**
     *
     */
    public static void countScoresPlayers(){
        for (Map.Entry<User, ArrayList<String>> entry : answersOfPlayers.entrySet()) {
            int score = entry.getValue().stream()
                    .mapToInt(String::length)
                    .sum();// sum of all characters

            User user = entry.getKey();
            pointsPerRound.put(user, score);
        }
    }

    /**
     *
     * @return
     */
    public static String updateRoundWinner() {
        User winner = null;
        int highestScore = 0;

        for (Map.Entry<User, Integer> entry : pointsPerRound.entrySet()) {
            if (entry.getValue() > highestScore) {
                highestScore = entry.getValue();
                winner = entry.getKey();

            }
        }
        return winner.getUsername()+"/"+highestScore;
    }
}
