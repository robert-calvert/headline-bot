package dev.robert.bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WordsManager {

    private List<Word> words = new ArrayList<>();

    public WordsManager(List<String> list) {
        for (String status : list) {
            List<String> split = Arrays.asList(status.split(" "));

            for (int i = 0; i < split.size(); i++) {
                if (!(split.get(i).toLowerCase().contains("http"))) {
                    Word word = getWord(split.get(i));
                    if (i == 0) word.setStarter();

                    if (i != (split.size() - 1)) {
                        // I avoid links as they detract from the content of the tweet.
                        if (split.get(i + 1).toLowerCase().contains("http"))
                            continue;

                        Word after = getWord(split.get(i + 1));

                        Word existing = word.getExistingAfter(after.getWord());
                        int instances = (existing != null ? (word.getWordsAfter().get(existing) + 1) : 1);
                        word.getWordsAfter().put(after, instances);
                    }
                }
            }
        }
    }

    // Builds a status of the given word length.
    public String getStatus(int length) {
        StringBuilder builder = new StringBuilder();
        Word current = pickStarter();

        for (int i = 0; i < length; i++) {
            if (current == null)
                continue;

            // If the next word will make the tweet exceed 140 characters, return.
            if ((builder.length() + current.getWord().length()) > 140)
                return filter(builder.toString());

            builder.append(current.getWord()).append(" ");
            current = current.getMostLikely();
        }

        return filter(builder.toString());
    }

    // Filter the starter words, and pick one at random.
    private Word pickStarter() {
        List<Word> starters = filterStarters();
        return starters.get(new Random().nextInt(starters.size()));
    }

    private List<Word> filterStarters() {
        List<Word> startingWords = new ArrayList<>();

        for (Word word : words) {
            if (word.isStarter())
                startingWords.add(word);
        }

        return startingWords;
    }

    // Returns a pre-existing word object, or creates a new one if it doesn't exist.
    private Word getWord(String word) {
        for (Word w : words) {
            if (w.getWord().equalsIgnoreCase(word)) {
                return w;
            }
        }

        Word newWord = new Word(word);
        words.add(newWord);
        return newWord;
    }

    // Removes some punctuation.
    private String filter(String status) {
        return status.replace("'", "").replace("\"", "");
    }
}
