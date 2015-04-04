package com.optimaize.webcrawlerverifier;

import org.jetbrains.annotations.NotNull;

/**
 * Result returned by the {@link KnownCrawlerDetector#detect} method.
 */
public class KnownCrawlerResult {

    @NotNull
    private final String identifier;
    @NotNull
    private final KnownCrawlerResultStatus status;

    public KnownCrawlerResult(@NotNull String identifier, @NotNull KnownCrawlerResultStatus status) {
        this.identifier = identifier;
        this.status = status;
    }

    /**
     * @see com.optimaize.webcrawlerverifier.bots.CrawlerData#getIdentifier()
     */
    @NotNull
    public String getIdentifier() {
        return identifier;
    }

    /**
     */
    @NotNull
    public KnownCrawlerResultStatus getStatus() {
        return status;
    }


    @Override
    public String toString() {
        return "KnownCrawlerResult{" +
                "identifier='" + identifier + '\'' +
                ", status=" + status +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KnownCrawlerResult result = (KnownCrawlerResult) o;

        if (status != result.status) return false;
        if (!identifier.equals(result.identifier)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = identifier.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }

}
