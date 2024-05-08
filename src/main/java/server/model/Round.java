package server.model;

import org.omg.CORBA.StringHolder;
import server.controller.User;

import java.util.*;

public class Round extends TimerTask{
    /*
    following variables changes every newRound
     */
    private HashMap<String, Integer> allAnswers = new HashMap<>();
    private HashMap<User, Integer> pointsPerRound = new HashMap<>();// resets value every round
    private HashMap<User, ArrayList<String>> answersOfPlayers = new HashMap<>();

    private String letters;
    private int timerCount;

    private int roundCount;

    private ArrayList<User> players = new ArrayList<>();

    private Game game;
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

        this.players = players;
    }

    public int getTimerCount() {
        return timerCount;
    }

    public void setTimerCount(int timerCount) {
        this.timerCount = timerCount;
    }

    /**
     * Starts a new round
     * @param roundCount round count to monitor what round is executing
     */

    public void newRound (int roundCount){
        System.out.println("Round "+roundCount +"!");
        //Reset list containing all answers
        allAnswers = new HashMap<>();
        // Reset points to 0 for each user
        pointsPerRound.forEach((user, points) -> pointsPerRound.put(user, 0));
        // Clear answers for each user
        answersOfPlayers.forEach((user, list) -> list.clear());

        this.roundCount = roundCount;
        getLetterChoice();

        System.out.println("Player size: " + players.size());

        for (User temp: players){

            System.out.println(letters);
            temp.getUserCallback().getLetterChoice(letters);

        }
        //the following methods are executed after the timer of round is finished

    }

    /**
     * method adds the valid answer to the list of the corresponding user
     * @param username source of answer
     * @param answer input answer
     */
    public void addAnswerToPlayer(String username,String answer){
        addToAllAnswersList(answer);
        for (Map.Entry<User, ArrayList<String>> entry : answersOfPlayers.entrySet()) {
            if (entry.getKey().getUsername().equalsIgnoreCase(username)){
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
    private void addToAllAnswersList(String answer){
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
    public void filterAnswers(){
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
    public void countScoresPlayers(){
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
    public String getWinnerOfRound() {
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

    private static void shuffleStringBuilder(StringBuilder sb, Random random) {
        for (int i = sb.length() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(j));
            sb.setCharAt(j, temp);
        }
    }

    public void getLetterChoice() {// return the random 20 letters
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
        // result.append(consonantsBuilder).append("\n.").append(vowelsBuilder);

        result.append(consonantsBuilder).append(vowelsBuilder);

        letters = result.toString();


    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    @Override
    public void run() {
        timerCount++;

        if (timerCount == 30){

        filterAnswers();
        countScoresPlayers();

        String[] winnerOfCurrentRound = getWinnerOfRound().split("/");//Format username/highscore
        game.checkWinner(winnerOfCurrentRound);
        System.out.println("Winner for round "+ roundCount+" is"+ winnerOfCurrentRound[0]
                +" with a score of "+ winnerOfCurrentRound[1]);

        game.startGame(players);

        cancel();

        } else {

            System.out.println("Round Counting -> " + timerCount);
            for (User temp: players){
                temp.getUserCallback().getRoundTime(timerCount);
            }
        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
