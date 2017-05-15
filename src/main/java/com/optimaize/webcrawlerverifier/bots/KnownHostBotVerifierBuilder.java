package com.optimaize.webcrawlerverifier.bots;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.optimaize.webcrawlerverifier.dns.DnsjavaReverseDnsVerifier;
import com.optimaize.webcrawlerverifier.dns.ReverseDnsVerifier;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public class KnownHostBotVerifierBuilder {

    private CrawlerData crawlerData;
    private ReverseDnsVerifier dnsVerifier;
    private Cache<String, BotCheckerResult> dnsResultCache;


    public KnownHostBotVerifierBuilder crawlerData(@NotNull CrawlerData crawlerData) {
        if (this.crawlerData != null) throw new IllegalStateException("The crawlerData was set already!");
        this.crawlerData = crawlerData;
        return this;
    }

    /**
     */
    public KnownHostBotVerifierBuilder dnsVerifier(@NotNull ReverseDnsVerifier dnsVerifier) {
        if (this.dnsVerifier != null) throw new IllegalStateException("The dnsVerifier was set already!");
        this.dnsVerifier = dnsVerifier;
        return this;
    }

    /**
     * Uses the {@link DnsjavaReverseDnsVerifier} with the default name server(s) provided by the system.
     */
    public KnownHostBotVerifierBuilder dnsVerifierDefault() {
        return dnsVerifier(new DnsjavaReverseDnsVerifier());
    }

    /**
     * Useful and required because verifications are expensive.
     * If you must, for testing, then pass in a dummy cache that drops all.
     */
    public KnownHostBotVerifierBuilder dnsResultCache(@NotNull Cache<String, BotCheckerResult> dnsResultCache) {
        if (this.dnsResultCache != null) throw new IllegalStateException("The dnsResultCache was set already!");
        this.dnsResultCache = dnsResultCache;
        return this;
    }

    /**
     * Uses maximumSize(1_000) and expireAfterWrite(3*24, TimeUnit.HOURS)
     */
    public KnownHostBotVerifierBuilder dnsResultCacheDefault() {
        Cache<String, BotCheckerResult> cache = CacheBuilder.newBuilder()
                .maximumSize(1_000)
                .expireAfterWrite(3 * 24, TimeUnit.HOURS)
                .build();
        return dnsResultCache(cache);
    }


    @NotNull
    public KnownHostBotVerifier build() {
        if (dnsVerifier == null) throw new IllegalArgumentException("No dnsVerifier provided!");
        if (dnsResultCache == null) throw new IllegalArgumentException("No cache provided!");
        return new KnownHostBotVerifierImpl(crawlerData, dnsVerifier, dnsResultCache);
    }

}
