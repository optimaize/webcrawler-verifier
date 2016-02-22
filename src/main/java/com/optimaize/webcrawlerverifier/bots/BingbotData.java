package com.optimaize.webcrawlerverifier.bots;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

/**
 * Resources:
 * http://en.wikipedia.org/wiki/Bingbot
 * http://www.bing.com/webmaster/help/how-to-verify-bingbot-3905dc26
 * They also encourage the reverse and forward DNS lookup verification.
 * Ending with search.msn.com
 *
 * This detects the Bingbot, plus the former msnbot.
 *
 */
public class BingbotData implements CrawlerData {

    private static final Predicate<String> PREDICATE = new Predicate<String>() {
        @Override
        public boolean apply(String userAgent) {
            //see http://en.wikipedia.org/wiki/Bingbot
            if (userAgent.contains("bingbot")) return true;

            //see http://en.wikipedia.org/wiki/Msnbot
            //this was the previous bot.
            //"As of February 2016 msnbot is still active ... (it was announced to retire "soon" quote some time ago...)"
            if (userAgent.contains("msnbot")) return true;

            return false;
        }
    };

    private static final ImmutableSet<String> HOSTNAMES = ImmutableSet.of("search.msn.com");


    private static final BingbotData INSTANCE = new BingbotData();
    public static BingbotData getInstance() {
        return INSTANCE;
    }
    private BingbotData() {
    }


    @NotNull
    @Override
    public String getIdentifier() {
        return "BINGBOT";
    }

    @NotNull
    @Override
    public Predicate<String> getUserAgentChecker() {
        return PREDICATE;
    }

    @NotNull
    @Override
    public Set<String> getIps() {
        return Collections.emptySet();
    }

    @NotNull
    @Override
    public Set<String> getHostnames() {
        return HOSTNAMES;
    }
}
