import java.util.*;

/**
 * This class is used for testing some methods
 * NOT INCLUDED IN FINAL OUTPUT
 */

public class test {
    private static HashMap<User, ArrayList<String>> answers = new HashMap<>();

    // Method to remove duplicates from all ArrayLists in a HashMap
    private static void removeDuplicates(HashMap<User, ArrayList<String>> map) {
        for (ArrayList<String> list : map.values()) {
            Set<String> unique = new HashSet<>(list);
            list.clear();
            list.addAll(unique);
        }
    }

    public static void main(String[] args) {
        // Populate the HashMap with some sample data
        ArrayList<String> list1 = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        ArrayList<String> list2 = new ArrayList<>(Arrays.asList("b", "e", "f", "g"));
        ArrayList<String> list3 = new ArrayList<>(Arrays.asList("f", "g", "h"));
        answers.put(new User("key1"), list1);
        answers.put(new User("key2"), list2);
        answers.put(new User("key3"), list3);

        // Remove duplicates from all ArrayLists
        removeDuplicates(answers);

        // Output the updated HashMap
        for (Map.Entry<User, ArrayList<String>> entry : answers.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
        }
    }

    // User class for using as keys in HashMap
    static class User {
        private String key;

        public User(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return key;
        }
    }
}
