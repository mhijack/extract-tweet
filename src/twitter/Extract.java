/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Arrays;

/**
 * Extract consists of methods that extract information from a list of tweets.
 *
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     *
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        Instant start;
        Instant end;
        long[] timestamps = new long[tweets.size()];

        // Loop through every tweet:
        for (int i = 0; i < tweets.size(); i++) {
            // Convet instant into seconds and save in timestamps array
            timestamps[i] = tweets.get(i).getTimestamp().getEpochSecond();
        }
        // Find minimum (start) & maximum (end)
        Arrays.sort(timestamps);
        start = Instant.ofEpochSecond(timestamps[0]);
        end = Instant.ofEpochSecond(timestamps[timestamps.length - 1]);

        // Create and return Timespan instance based on start and end
        return new Timespan(start, end);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     *
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> usernames = new HashSet<>();
        for (Tweet tweet: tweets) {
            usernames.addAll(returnUsername(tweet));
        }
        return usernames;
    }

    /**
     * Get usernames mentioned in a String of a single tweet text.
     *
     * @param tweet
     *
     * @return an set of username mentioned in the tweet text.
     */
    public static Set<String> returnUsername(Tweet tweet) {
        String tweetText = tweet.getText();
        Set<String> usernames = new HashSet<>();

        do {
            int startIndex = tweetText.indexOf('@');
            if (startIndex == -1) {
                break;
            } else if (startIndex == 0 || Character.isLetter(tweetText.charAt(startIndex - 1))) {
                tweetText = tweetText.substring(startIndex + 1);
            } else {
                String username = "";

                while (Character.isLetter(tweetText.charAt(startIndex + 1))) {
                    username = "" + username + tweetText.charAt(startIndex + 1);
                    startIndex += 1;
                }

                username = username.toLowerCase();
                usernames.add(username);

                tweetText = tweetText.substring(startIndex + 1);
            }

        } while (tweetText.indexOf('@') != -1);
        return usernames;
    }
}
