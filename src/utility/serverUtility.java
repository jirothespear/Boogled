package src.utility;

/**
 * this is a reference interface
 * basisi for corba
 */
public interface serverUtility {
    boolean checkUser(String userNPasswd);// to check credentials for user log in
    boolean checkWord( String answer);// use to check word count and word validity
    void start(String user); // user joins or creates a game
    String showWinner();// use to return user who won a round || game
    int showScore(String user);// show score of a user
    String getLetterChoice();

}
