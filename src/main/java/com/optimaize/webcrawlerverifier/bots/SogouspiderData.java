package com.optimaize.webcrawlerverifier.bots;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

/**
 * TODO for now not supported, unclear how to identify.
 *
 *
 *
 * Resources:
 * http://searchenginewatch.com/sew/news/2067357/bye-bye-crawler-blocking-parasites
 * http://en.wikipedia.org/wiki/Sogou
 */
public class SogouspiderData implements CrawlerData {

    private static final Predicate<String> PREDICATE = new Predicate<String>() {
        @Override
        public boolean apply(String userAgent) {
            if (userAgent.toLowerCase(Locale.ENGLISH).contains("sogou")) return true;
            return false;
        }
    };

    /**
     * Nah, apparently they don't identify as this or any other domain? Dunno...
     * I'd need a nofficial statement...
     */
    private static final ImmutableSet<String> HOSTNAMES = ImmutableSet.of("sogou.com");


    private static final SogouspiderData INSTANCE = new SogouspiderData();
    public static SogouspiderData getInstance() {
        if (true) throw new UnsupportedOperationException("Not supported yet.");
        return INSTANCE;
    }
    private SogouspiderData() {
    }


    @NotNull
    @Override
    public String getIdentifier() {
        return "SOGOUSPIDER";
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
