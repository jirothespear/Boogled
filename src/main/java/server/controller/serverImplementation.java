package server.controller;

import server.model.Game;
import server.model.Round;
import sqlconnector.DataPB;

import java.util.ArrayList;
import java.util.Random;

public class serverImplementation implements serverUtility {
    private Game game = new Game();
    private serverImplementation(){
        DataPB.setCon();
    }

    @Override
    public boolean checkUser(String userNPasswd) {// Format "username/password"
        return DataPB.checkUser(userNPasswd);
    }

    @Override
    public String checkWord(String  userNAnswer) {// Format "username/answer"
        String input[] = userNAnswer.split("/");
        User user = game.findUser(input[0]);
        String answer = input[1];
        if(answer.length()>=4 && DataPB.checkWord(answer)){
            Round.addAnswerToPlayer(user,answer);
            return userNAnswer;// return parameter, answer is valid
        }
        return "*";
    }

    @Override
    public void start(String user) {
        ArrayList<User> players = new ArrayList<>();
        // get users from queueing system
        // for loop in converting string from queueinng system to users, from list of string username to list of user objects
        // use game.findUser(String username); to convert username to user object
        game = new Game();
        game.startGame(players);
    }

    @Override
    public String showWinnerOfRound() {
        return game.getWinnerOfRound();
    }

    @Override
    public int showScore(String user) {// shows the score of user in the game
        return 0;
    }

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

    @Override
    public void startRound() {
        game.newRound();
    }
    public boolean checkIfChampionExist(){
        return !(game.getChampion().getUsername().equalsIgnoreCase("null"));
    }

    @Override
    public String showChampion() {
        return  game.getChampion().getUsername();
    }

    @Override
    public String getLeaderBoard() {
        return null;
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
}
