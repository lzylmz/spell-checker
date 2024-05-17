//Fırat bilgen 22050151020
// Mustafa yılmaz 20050111010
// Hasan uslu 19050111003
// Batuhan tuncer 20050111040

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class FindInDictionary {
    public FindInDictionary() {
    }

    // Builds a HashSet containing words from the given file path.
    public HashSet<String> buildHashSet(String filePath) {
        HashSet<String> set = new HashSet();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            String line;
            try {
                // Read each line from the file, trim and convert to lowercase, then add to the HashSet.
                while((line = br.readLine()) != null) {
                    set.add(line.trim().toLowerCase());
                }
            } catch (Throwable var7) {
                try {
                    br.close();
                } catch (Throwable var6) {
                    var7.addSuppressed(var6);
                }

                throw var7;
            }

            // Close the BufferedReader.
            br.close();
        } catch (IOException var8) {
            var8.printStackTrace();
        }

        return set;
    }

    // Checks if the given word is present in the HashSet, ignoring case.
    public boolean searchWord(HashSet<String> set, String word) {
        return set.contains(word.toLowerCase());
    }
}
