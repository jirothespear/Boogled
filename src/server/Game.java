package src.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Game {
    private ArrayList<User>  players = new ArrayList<>();// list of players
    private HashMap<User, Integer> overallPoints = new HashMap<>();// stores points
    private HashMap<User, Integer> pointsPerRound = new HashMap<>();// resets value every round
    private HashMap<User, Integer> playerPlacing = new HashMap<>(); // stores the number of wins per player
    private Boolean isOpen;// status for game if waiting or not

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
    public void startGame(){
        isOpen= false;
        for (int i =0; i < players.size(); i++){
            overallPoints.put(players.get(i),0);
            pointsPerRound.put(players.get(i),0);
            playerPlacing.put(players.get(i),0);
        }
    }
    public void newRound(){
        for (Map.Entry<User, Integer> entry : pointsPerRound.entrySet()) {
            entry.setValue(0);
        }
    }
    public static ArrayList<Character> generateRandomLetters() {// place each letter in a button to prevent human error
        ArrayList<Character> result = new ArrayList<>();
        Random random = new Random();
        String consonants = "bcdfghjklmnpqrstvwxyz"; // Consonants
        String vowels = "aeiou"; // Vowels

        // Generate 13 consonants
        for (int i = 0; i < 13; i++) {
            int index = random.nextInt(consonants.length());
            result.add(consonants.charAt(index));
        }

        // Generate 7 vowels
        for (int i = 0; i < 7; i++) {
            int index = random.nextInt(vowels.length());
            result.add(vowels.charAt(index));
        }

        // Shuffle the letters in the list
        shuffleList(result, random);

        return result;

    }

    // Method to shuffle a StringBuilder
    private static void shuffleList(ArrayList<Character> list, Random random) {
        for (int i = list.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }
    }
}
