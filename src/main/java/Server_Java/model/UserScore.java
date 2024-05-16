package Server_Java.model;

public class UserScore implements Comparable<UserScore>{

    private String username;

    private String password;

    private int score;

    public UserScore(String username, String password, int score) {
        this.username = username;
        this.password = password;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(UserScore o) {

        if (this.getScore() > o.getScore()){
            return 1;
        } else if (this.getScore() < o.getScore()){
            return -1;

        } else return  0;

    }
}
