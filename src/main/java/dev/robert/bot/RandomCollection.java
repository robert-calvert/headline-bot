package dev.robert.bot;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class RandomCollection<Word> {

    /*
    A utility class which makes it easy to choose the next word in a tweet.
     */

    private final NavigableMap<Double, Word> map = new TreeMap<>();
    private final Random random;
    private double total = 0;

    public RandomCollection() {
        this(new Random());
    }

    public RandomCollection(Random random) {
        this.random = random;
    }

    public void add(double weight, Word result) {
        total += weight;
        map.put(total, result);
    }

    public Word next() {
        double value = random.nextDouble() * total;

        if (total == 0 && value == 0) {
            return map.get(0.5);
        } else {
            return map.ceilingEntry(value).getValue();
        }
    }
}
