package Server_Java.model;

import java.util.*;
import CORBA_IDL.Utility.*;
import java.util.concurrent.ConcurrentHashMap;

public class Round extends TimerTask{
    /*
    following variables changes every newRound
     */
    private ConcurrentHashMap<String, Integer> allAnswers = new ConcurrentHashMap<>();
    private ConcurrentHashMap<User, Integer> pointsPerRound = new ConcurrentHashMap<>();// resets value every round
    private ConcurrentHashMap<User, ArrayList<String>> answersOfPlayers = new ConcurrentHashMap<>();

    private String letters;
    public int timerCount = 13;

    private int roundCount = 0;

    private ArrayList<User> players;

    private Game game;

    public ArrayList<UserScore> roundPoint = new ArrayList<>();
    /**
     * Constructor
     * @param players list of players to be used for Maps
     */
    public Round (ArrayList<User>  players){
        allAnswers = new ConcurrentHashMap<>();
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
        this.roundCount = roundCount;
        setChoiceLetters();

        try {
            for (User temp : players) {
                System.out.println(letters);
                temp.getUserCallback().getLetterChoice(letters);
            }

        } catch (Exception e){

            System.out.println("cannot connect to user..");
        }
        System.out.println("Round "+roundCount +"!");
        //Reset list containing all answers
        allAnswers = new ConcurrentHashMap<>();
        // Reset points to 0 for each user
        pointsPerRound.forEach((user, points) -> pointsPerRound.put(user, 0));
        // Clear answers for each user
        answersOfPlayers.forEach((user, list) -> list.clear());
        System.out.println("Player size: " + players.size());
    }

    @Override
    public void run() {


        System.out.println("size - >" + players.size());
        if (timerCount == -3){
            filterAnswers();
            countScoresPlayers();
            String[] winnerOfCurrentRound = getWinnerOfRound().split("/");//Format username/highscore
            game.checkWinner(winnerOfCurrentRound);
            System.out.println("Winner for round "+ roundCount +" is"+ winnerOfCurrentRound[0]
                    +" with a score of "+ winnerOfCurrentRound[1]);
            if (!winnerOfCurrentRound[0].equals("null")){
                System.out.println("There is a winner");
                for(User temp: players){
                    temp.getUserCallback().roundEnd(winnerOfCurrentRound[0], Integer.parseInt(winnerOfCurrentRound[1]));
                }
            }

            game.setPointsPrevious(roundPoint);
            allAnswers = new ConcurrentHashMap<>();
            pointsPerRound = new ConcurrentHashMap<>();
            answersOfPlayers = new ConcurrentHashMap<>();
            roundCount = 0;
            roundPoint = new ArrayList<>();
            players = new ArrayList<>();
            cancel();
            game.startGame();


        } else if (timerCount == -1 || timerCount == -2) {

            System.out.println("buffer time");
        }else {

            if (players.size() == 0){
                cancel();
            }
            System.out.println("Round Counting -> " + timerCount);
            for (User temp: players){

                try {
                    temp.getUserCallback().getRoundTime(timerCount);

                } catch (Exception e){
                    System.out.println("cannot connect to client.");
                }
            }
        }
        timerCount--;
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
        if (answersOfPlayers.size() < 2) {
            for (Map.Entry<User, ArrayList<String>> entry : answersOfPlayers.entrySet()) {// loop per user
                ArrayList<String> userAnswers = entry.getValue();
                for (int i = 0; i < userAnswers.size(); i++) {// loop for answers of user
                    for (Map.Entry<String, Integer> ety : allAnswers.entrySet()) {// loop for all answers
                        if (ety.getKey().equalsIgnoreCase(userAnswers.get(i))) {
                            if (ety.getValue() > 1) {
                                // remove

                                if (i == 0) {
                                    userAnswers.remove(i + 1);
                                    i--;
                                } else {
                                    userAnswers.remove(i);
                                    i--;
                                }
                            }
                        }
                    }
                    answersOfPlayers.put(entry.getKey(), userAnswers);
                }

            }

        } else if(answersOfPlayers.size() > 2) {
            for (Map.Entry<User, ArrayList<String>> entry : answersOfPlayers.entrySet()) {// loop per user
                ArrayList<String> userAnswers = entry.getValue();
                for (int i = 0; i < userAnswers.size(); i++) {// loop for answers of user
                    for (Map.Entry<String, Integer> ety : allAnswers.entrySet()) {// loop for all answers
                        if (ety.getKey().equalsIgnoreCase(userAnswers.get(i))) {
                            if (ety.getValue() > 1) {
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

//        String winner = "";
//        int highestScore = 0;
//
//
//
        for (Map.Entry<User, Integer> entry : pointsPerRound.entrySet()) {
//
           roundPoint.add(new UserScore(entry.getKey().getUsername(), entry.getKey().getPassword(), entry.getValue()));
//
       }
        System.out.println("size scores to be filtered " + roundPoint.size());
        Collections.sort(roundPoint);
//
//        if (roundPoint.size() == 1){
//            winner = "null";
//        }
//        else if (roundPoint.get(roundPoint.size()-1).getScore()
//                == roundPoint.get(roundPoint.size()-2).getScore()){
//
//            winner = "null";
//        } else {
//            winner = roundPoint.get(roundPoint.size()-1).getUsername();
//            highestScore = roundPoint.get(roundPoint.size()-1).getScore();
//        }
//
//        return winner+"/"+highestScore;


        User winner = new User();
        int highestScore = 0;
        for (Map.Entry<User, Integer> entry : pointsPerRound.entrySet()) {
            if (entry.getValue() > highestScore) {
                highestScore = entry.getValue();
                winner = entry.getKey();

            }
        }

        if (winner.getUsername() == null){

            return "null/0";
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

    public String setChoiceLetters() {// return the random 20 letters
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


        return letters;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }



    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public ArrayList<UserScore> getRoundPoint() {
        return roundPoint;
    }



}
