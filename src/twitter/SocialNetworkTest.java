/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import org.junit.Test;

import java.time.Instant;
import java.util.*;

import static org.junit.Assert.assertTrue;

public class SocialNetworkTest {

    /*
     * TODO: testing strategies...
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

    private static final Tweet tweet1 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "asdf @jack rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(2, "Jack", "asdf @jack rivest talk in 30 minutes #hype", d2);

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());

        assertTrue("expected empty graph", followsGraph.isEmpty());

        Map<String, Set<String>> followsGraph2 = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2));

        assertTrue("expected graph with 1 key", followsGraph2.size() == 1);

        Set<String> jackSet = new HashSet<>();
        jackSet.add("jack");
        assertTrue("expected 'bbitdiddle' mapped to 'jack'", followsGraph2.get("bbitdiddle").equals(jackSet));

        Map<String, Set<String>> followsGraph3 = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet3));
        assertTrue("expected graph has 1 key", followsGraph3.size() == 1);
        assertTrue("expected empty graph", followsGraph3.get("Jack".toLowerCase()).isEmpty());
    }

    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertTrue("expected empty list", influencers.isEmpty());

        Map<String, Set<String>> followsGraph2 = new HashMap<>();
        Set<String> followers = new HashSet<>();
        Set<String> followers2 = new HashSet<>();
        followers.add("melody");
        followers.add("clow");
        followers.add("jason");
        followers2.add("jack");
        followers2.add("jason");
        followsGraph2.put("jack", followers);
        followsGraph2.put("melody", followers2);
        List<String> influencers2 = SocialNetwork.influencers(followsGraph2);

        assertTrue("expected 'jason' at index 0", influencers2.get(0).equals("jason"));
        // assertEquals("jack", influencers2);
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     *
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
