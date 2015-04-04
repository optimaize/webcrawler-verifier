package com.optimaize.webcrawlerverifier.dns;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * These are not unit tests, they are real world data tests. They may fail if
 *  - a provider has changed their network setup
 *  - internet drops
 */
public class DnsjavaReverseDnsVerifierTest {

    @Test
    public void testVerify() throws Exception {
        DnsjavaReverseDnsVerifier verifier = new DnsjavaReverseDnsVerifier();

        //has reverse dns:
        assertTrue(verifier.verify("66.249.66.1", "googlebot.com"));

        //[currently] doesn't have:
        assertFalse(verifier.verify("80.74.147.43", "optimaize.com"));
    }

}