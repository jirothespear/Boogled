package server.model;

import java.util.ArrayList;

public class Round {
    private int roundNumber;
    private ArrayList<Character> letters;
    private long startTime;
    private long endTime;

    public Round(int roundNumber, ArrayList<Character> letters) {
        this.roundNumber = roundNumber;
        this.letters = letters;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public ArrayList<Character> getLetters() {
        return letters;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getRoundDuration() {
        return endTime - startTime;
    }
}
