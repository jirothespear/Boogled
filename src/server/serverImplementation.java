package src.server;

import src.utility.serverUtility;

import java.util.Random;

public class serverImplementation implements serverUtility {
    @Override
    public boolean checkUser(String userNPasswd) {
        return false;
    }

    @Override
    public boolean checkWord(String answer) {
        return false;
    }

    @Override
    public void start(String user) {
        // check if there is an existing game && game is still accepting players
    }

    @Override
    public String showWinner() {
        return null;
    }

    @Override
    public int showScore(String user) {
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
