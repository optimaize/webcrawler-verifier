package com.optimaize.webcrawlerverifier;

import com.google.common.base.Optional;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public interface KnownCrawlerDetector {

    /**
     * @param userAgent see {@link com.optimaize.webcrawlerverifier.bots.KnownHostBotVerifier#check(String, String)}
     * @param ip see {@link com.optimaize.webcrawlerverifier.bots.KnownHostBotVerifier#check(String, String)}
     * @return absent if none detected.
     */
    @NotNull
    Optional<KnownCrawlerResult> detect(@NotNull String userAgent, @NotNull String ip);

}
