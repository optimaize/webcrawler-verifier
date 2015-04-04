package com.optimaize.webcrawlerverifier.bots;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

/**
 * Resources:
 * https://duckduckgo.com/duckduckbot
 */
public class DuckduckbotData implements CrawlerData {

    private static final Predicate<String> PREDICATE = new Predicate<String>() {
        @Override
        public boolean apply(String userAgent) {
            if (userAgent.contains("DuckDuckBot")) return true;
            return false;
        }
    };

    /**
     * As documented by duckduckgo: https://duckduckgo.com/duckduckbot
     */
    private static final ImmutableSet<String> IPS = ImmutableSet.of("72.94.249.34", "72.94.249.35", "72.94.249.36", "72.94.249.37", "72.94.249.38");


    private static final DuckduckbotData INSTANCE = new DuckduckbotData();
    public static DuckduckbotData getInstance() {
        return INSTANCE;
    }
    private DuckduckbotData() {
    }


    @NotNull
    @Override
    public String getIdentifier() {
        return "DUCKDUCKBOT";
    }

    @NotNull
    @Override
    public Predicate<String> getUserAgentChecker() {
        return PREDICATE;
    }

    @NotNull
    @Override
    public Set<String> getIps() {
        return IPS;
    }

    @NotNull
    @Override
    public Set<String> getHostnames() {
        return Collections.emptySet();
    }
}
