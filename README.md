# The Headline Bot
https://twitter.com/theheadlinebot

Developed by Robert Calvert (@robxcalvert)

A twitter bot designed to create incoherent but potentially funny headlines based on current events.
Using a crude implementation of the Markov chain, it gathers every word of 18 news sources' last 200 tweets,
as well as the word after it. It then uses these words to generate headlines by following each word with a
word which followed it in a real tweet. This way the headlines may make *some* sense, but will still be random.
I have attempted to use both left-wing, center and right-wing Twitters to get a nice sample of headlines.
I also add Donald Trump's tweets to the mix to add some comedic value.

Developed using the Twitter4J API.