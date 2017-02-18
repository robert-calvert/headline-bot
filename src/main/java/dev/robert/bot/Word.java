package dev.robert.bot;

import java.util.HashMap;
import java.util.Map;

public class Word {

    /*
    Represents a word in a tweet.
    This object stores whether or not it was a starting word,
    and also the words which followed it, and how many times that following word appeared.
     */

    private String word;
    private Map<Word, Integer> wordsAfter;
    private boolean starter;

    public Word(String word) {
        this.word = word;
        this.wordsAfter = new HashMap<>();
        this.starter = false;
    }

    // Returns the most likely word to appear after this one.
    public Word getMostLikely() {
        RandomCollection<Word> collection = new RandomCollection<>();
        for (Word key : wordsAfter.keySet()) {
            collection.add(wordsAfter.get(key), key);
        }

        return collection.next();
    }

    // Returns the word object of an existing following word, or null if it doesn't exist.
    public Word getExistingAfter(String word) {
        for (Word w : wordsAfter.keySet()) {
            if (w.getWord().equals(word))
                return w;
        }

        return null;
    }

    public String getWord() {
        return word;
    }

    public Map<Word, Integer> getWordsAfter() {
        return wordsAfter;
    }

    public boolean isStarter() {
        return starter;
    }

    public void setStarter() {
        this.starter = true;
    }
}
