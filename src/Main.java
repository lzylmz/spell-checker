//Fırat bilgen 22050151020
// Mustafa yılmaz 20050111010
// Hasan uslu 19050111003
// Batuhan tuncer 20050111040

import java.util.Iterator;
import java.util.List;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {


        SpellChecker spellChecker = new SpellChecker();
        String inputText = "Thes are some misspelled words like aplle, banan and oragne.";

        spellChecker.isWordInDictionary(inputText);    // Check if the input text contains valid words and identify misspelled words.
        List<String> misspelledWords = spellChecker.getMisspelledWord();      // Retrieve the list of misspelled words.
        Iterator var4 = misspelledWords.iterator();     // Iterate through the misspelled words.
        while (var4.hasNext()) {
            String misspelledWord = (String) var4.next();
            List<String> suggestions = spellChecker.findSuggestions(misspelledWord);    // Find suggestions for the misspelled word.
            System.out.println("Suggestions for " + misspelledWord + ": " + String.valueOf(suggestions));
        }
    }
}
