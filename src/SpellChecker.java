//Fırat bilgen 22050151020
// Mustafa yılmaz 20050111010
// Hasan uslu 19050111003
// Batuhan tuncer 20050111040

import java.util.*;

public class SpellChecker {
    // Create an instance of FindInDictionary to handle dictionary operations.
    FindInDictionary mydic = new FindInDictionary();

    // List to store misspelled words.
    ArrayList<String> misspell_word = new ArrayList<>();

    // HashSet to store dictionary words.
    private HashSet<String> dic = mydic.buildHashSet("top_100000_words.txt");

    // QWERTY keyboard layout for calculating keyboard distance.
    private static final char[][] QWERTY_LAYOUT = {
            {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'},
            {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'},
            {'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Ç'},
            {'^', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', '<', '>'}
    };

    // Getter method to retrieve the list of misspelled words.
    public List<String> getMisspelledWord() {
        return this.misspell_word;
    }

    // Check if the given text contains valid words.
    public void isWordInDictionary(String text) {
        // Split the input text into an array of words.
        String[] str_Arr = text.split("[\\s.,;!?]+");

        // Check each word against the dictionary and add misspelled words to the list.
        for (String word : str_Arr) {
            if (!mydic.searchWord(dic, word)) {
                misspell_word.add(word);
            }
        }

        // Display misspelled words if any.
        if (!misspell_word.isEmpty()) {
            System.out.println("Misspelled words:");
            for (String misspelledWord : misspell_word) {
                System.out.println(misspelledWord);
            }
        }
    }

    // Find suggestions for a misspelled word.
    public List<String> findSuggestions(String misspelled) {
        List<String> suggestions = new ArrayList<>();

        // Iterate through the dictionary to find words with Levenshtein distance <= 2.
        for (String word : dic) {
            int distance = getEditDistance(misspelled, word);
            int keyboard_distance = getKeyboardDistance(misspelled, word, this.QWERTY_LAYOUT);

            // If Levenshtein distance is less than or equal to 2, add the word as a suggestion.
            if (distance <= 2) {
                suggestions.add(word);
            }
        }

        return suggestions;
    }

    // Calculate Levenshtein distance between two strings.
    public static int getEditDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost
                );
            }
        }

        return dp[m][n];
    }

    // Calculate keyboard distance between two strings based on a given keyboard layout.
    public int getKeyboardDistance(String s1, String s2, char[][] keyboardLayout) {
        int distance = 0;

        // Iterate through characters and calculate keyboard distance.
        for (int i = 0; i < Math.min(s1.length(), s2.length()); i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);

            int[] position1 = getKeyboardPosition(c1, keyboardLayout);
            int[] position2 = getKeyboardPosition(c2, keyboardLayout);

            // If characters are found in the keyboard layout, calculate distance.
            if (position1 != null && position2 != null) {
                distance += computeDistanceBetweenPositions(position1, position2);
            } else {
                // Add a cost if characters are not found in the keyboard layout.
                distance += 5; // Example cost addition
            }
        }

        return distance;
    }

    // Get the position of a character in a given keyboard layout.
    private int[] getKeyboardPosition(char c, char[][] keyboardLayout) {
        for (int i = 0; i < keyboardLayout.length; i++) {
            for (int j = 0; j < keyboardLayout[i].length; j++) {
                if (keyboardLayout[i][j] == c) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    // Calculate distance between two positions in the keyboard layout.
    private int computeDistanceBetweenPositions(int[] position1, int[] position2) {
        return Math.abs(position1[0] - position2[0]) + Math.abs(position1[1] - position2[1]);
    }
}
