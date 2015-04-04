package com.optimaize.webcrawlerverifier.bots;

/**
 *
 */
public enum BotCheckerResult {

    /**
     * The bot is the one that it is checked for.
     * Example: It claims to be Googlebot, and the request comes from Google.
     */
    IS,

    /**
     * The bot is not the one that was checked for. It's another user agent.
     */
    IS_NOT,

    /**
     * The bot claims to be the one that was checked for, but verification says it's not coming from
     * the robot's operator.
     * Example: It claims to be Googlebot, but the request is not from Google.
     */
    IMPERSONATOR,

    /**
     * Either a [temporary] networking error, or another unexpected runtime error coming from a
     * situation not anticipated or a software bug.
     */
    FAILED
    ;

    public static void assertSize(int expected) {
        if (values().length != expected)
            throw new AssertionError("Update the code calling this with " + expected + "!");
    }

}
