package server.controller;

/**
 * this is a reference interface
 * basisi for corba
 */
public interface serverUtility {
    boolean checkUser(String userNPasswd);// to check credentials for user log in
    String checkWord( String userNAnswer);// use to check word count and word validity
    void start(String user); // user joins or creates a game
    String showWinnerOfRound();// use to return user who won a round(DI PA AKO SURE DITO)
    int showScore(String user);// show score of a user(DI PA AKO SURE DITO)
    String getLetterChoice();// used to get choices for game
    void startRound();
    boolean checkIfChampionExist();// check if game already has a champion
    String showChampion();// get champion of game4

    String getLeaderBoard();
}
