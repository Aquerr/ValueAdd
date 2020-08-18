package pl.bartlomiejstepien.valueadd;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args)
    {
        final Path path = Paths.get(".").resolve("zadanie.txt");
        final Map<String, List<Integer>> wordLinesOccurrences = getWordOccurrencesInFile(path);

        for (final Map.Entry<String, List<Integer>> word : wordLinesOccurrences.entrySet())
        {
            System.out.printf("%s - %d - pozycje -> %s%n", word.getKey(), word.getValue().size(), Arrays.toString(word.getValue().toArray()));
        }
    }

    private static Map<String, List<Integer>> getWordOccurrencesInFile(final Path path)
    {
        if (path == null)
            throw new IllegalArgumentException("File path cannot be null!");

        final Map<String, List<Integer>> wordLinesOccurrence = new TreeMap<>();

        try
        {
            final LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(path.toFile()));

            String line;
            while ((line = lineNumberReader.readLine()) != null)
            {
                // Assigment does not say anything if we should ignore numbers... that is why we are counting them as words too.
                // another pattern that also works [^\p{Lu}\p{Ll}*]
                final String[] wordsInLine = line.split("[^A-Za-z0-9ĄĘĆÓŃŁŚŻŹąęćńółśżź]");

                for (final String word : wordsInLine)
                {
                    // Sometimes a text contains a dash with two whitespaces around it " - "
                    // Because text is spilt on "-", it will result in two "words" with one whitespace " ", " "
                    // Therefore we need to ignore such "words" as they are not really words.
                    if (word.trim().equals(""))
                        continue;

                    List<Integer> occurrences = wordLinesOccurrence.get(word.trim());
                    if (occurrences == null)
                        occurrences = new ArrayList<>();
                    occurrences.add(lineNumberReader.getLineNumber());
                    wordLinesOccurrence.put(word.trim(), occurrences);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return wordLinesOccurrence;
    }
}
