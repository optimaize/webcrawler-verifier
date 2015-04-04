package com.optimaize.webcrawlerverifier;

/**
 * Enum used in the KnownCrawlerResult.
 */
public enum KnownCrawlerResultStatus {

    /**
     * The bot is the one that it claims to be.
     * Example: It claims to be Googlebot, and the request comes from Google.
     */
    VERIFIED,

    /**
     * The bot claims to be one that it is not according to the verification process.
     * This answer can be wrong in case the library's data is outdated.
     * Example: It claims to be Googlebot, but the request is not from Google.
     */
    IMPERSONATOR,

    /**
     * Either a [temporary] networking error, or another unexpected runtime error coming from a
     * situation not anticipated or a software bug.
     *
     * How you handle this - grant access, deny access, grant limited access, log and alert your
     * webmaster... is up to you.
     */
    FAILED
    ;

    public static void assertSize(int expected) {
        if (values().length != expected)
            throw new AssertionError("Update the code calling this with " + expected + "!");
    }

}
