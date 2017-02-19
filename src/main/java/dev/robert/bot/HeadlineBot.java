package dev.robert.bot;

import twitter4j.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class HeadlineBot {

    /*
    The Headline Bot
    https://twitter.com/theheadlinebot
    Developed by Robert Calvert (@robxcalvert)

    A twitter bot designed to create incoherent but potentially funny headlines based on current events.
    Using a crude implementation of the Markov chain, it gathers every word of various news sources' last 500 tweets,
    as well as the word after it. It then uses these words to generate headlines by following each word with a
    word which followed it in a real tweet. This way the headlines may make *some* sense, but will still be random.

    I have attempted to use both left-wing, center and right-wing Twitters to get a nice sample of headlines.
    I also add Donald Trump's tweets to the mix to add some comedic value.

    Developed using the Twitter4J API.
     */

    private static Twitter twitter;

    // 50% chance that one of these prefixes is added to the front of a tweet.
    private static String[] prefixes = new String[] {
            "#LATEST", "#BREAKING", "TRUMP LATEST:", "JUST IN:", "Sources reveal:", "#NEWS", "Great news!", "#Trump"
    };

    // The 18 Twitter accounts that tweets are gathered from. May be subject to change.
    private static String[] accounts = new String[] {
            "realDonaldTrump", "foxNews", "cnn", "NewshubNZ", "MSNBC", "NBCNews", "infowars", "YahooNews",
            "BBCNews", "RT_com", "SkyNews", "nytimes", "HuffingtonPost", "guardian", "TheSun", "BBCWorld",
            "washingtonpost", "CBSNews", "AP", "business", "Reuters", "AJEnglish", "Telegraph", "CNNPolitics"
    };

    public static void main(String[] args) throws Exception {
        twitter = TwitterFactory.getSingleton();

        WordsManager manager = new WordsManager(getAll());
        String status = getPrefix() + manager.getStatus(16);
        twitter.updateStatus(status);

        System.out.println("[Tweet] " + status);
    }

    // 50% of the time, a random prefix is added.
    private static String getPrefix() {
        double d = ThreadLocalRandom.current().nextDouble();
        if (d > 0.5) {
            return prefixes[ThreadLocalRandom.current().nextInt(prefixes.length)] + " ";
        } else {
            return "";
        }
    }

    // Loads all tweets from the 18 accounts.
    private static List<String> getAll() {
        List<String> statuses = new ArrayList<>();
        for (String account : accounts) {
            try {
                statuses.addAll(getStatuses(account));
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }

        return statuses;
    }

    // Loads the last 200 tweets from the specified account.
    private static List<String> getStatuses(String username) throws TwitterException {
        List<String> list = new ArrayList<>();
        ResponseList<Status> statuses = twitter.getUserTimeline(username, new Paging(1, 500));

        for (Status status : statuses) {
            list.add(status.getText());
        }

        return list;
    }
}
