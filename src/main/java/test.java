import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is used for testing some methods
 * NOT INCLUDED IN FINAL OUTPUT
 */

public class test {
    public static void printhello(int timerCount) {
        final int[] count = {0};
        Timer timer = new Timer();
        final int[] delay = {0}; // Initial delay
        int period = 1000; // Repeat every 1 second
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (delay[0] >= timerCount * 1000) {
                    timer.cancel(); // Cancel the timer after 10 seconds
                    // callback for round ending
                    return;
                }
                // execute gathering of answers from players
                // TO DO:
                count[0]++;
                System.out.println("Hello " + count[0]);
                delay[0] += period;
            }
        }, delay[0], period);
    }

    public static void main(String[] args) {
        // Example usage:
        printhello(10); // Print "Hello" for 10 seconds
    }
}
