package com.optimaize.webcrawlerverifier;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.optimaize.webcrawlerverifier.bots.BotCheckerResult;
import com.optimaize.webcrawlerverifier.bots.KnownHostBotVerifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 */
public class DefaultKnownCrawlerDetector implements KnownCrawlerDetector {

    @NotNull
    private final List<KnownHostBotVerifier> verifiers;

    public DefaultKnownCrawlerDetector(@NotNull List<KnownHostBotVerifier> verifiers) {
        this.verifiers = ImmutableList.copyOf(verifiers);
    }

    @NotNull
    @Override
    public Optional<KnownCrawlerResult> detect(String userAgent, @NotNull String ip) {
        for (KnownHostBotVerifier verifier : verifiers) {
            BotCheckerResult check = verifier.check(userAgent, ip);
            if (check != BotCheckerResult.IS_NOT) {
                return Optional.of(new KnownCrawlerResult(verifier.getIdentifier(), convert(check)));
            }
        }
        return Optional.absent();
    }

    private KnownCrawlerResultStatus convert(BotCheckerResult check) {
        BotCheckerResult.assertSize(4);
        KnownCrawlerResultStatus.assertSize(3);
        switch (check) {
            case IS:
                return KnownCrawlerResultStatus.VERIFIED;
            case IMPERSONATOR:
                return KnownCrawlerResultStatus.IMPERSONATOR;
            case FAILED:
                return KnownCrawlerResultStatus.FAILED;
            case IS_NOT:
                throw new UnsupportedOperationException("Not convertible!");
            default:
                throw new UnsupportedOperationException(check.name());
        }
    }

}
