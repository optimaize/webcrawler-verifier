package com.optimaize.webcrawlerverifier.bots;

import org.jetbrains.annotations.NotNull;

/**
 * Verification for user-agents of known origin, such as Googlebot, bingbot etc that are documented as
 * coming from certain networks or having certain host names.
 *
 * <p>This is not for general-purpose crawler software that may be run by independent users.</p>
 *
 * <p>There are two kinds of implementations:
 * <ol>
 *   <li>Those with known hosts such as Googlebot coming from googlebot.com.
 *       These use a combination of DNS and reverse-DNS lookup.</li>
 *   <li>Those with a specified range of networks or ip addresses such as DuckDuckBot.
 *       These just compare the client's ip with the known crawler's network addresses.</li>
 * </ol></p>
 */
public interface KnownHostBotVerifier {

    /**
     * @see com.optimaize.webcrawlerverifier.bots.CrawlerData#getIdentifier()
     */
    @NotNull
    String getIdentifier();

    /**
     * @param userAgent As read from the http header "User-Agent" field.
     *                  You may pass in the empty string if the header field is empty or if it's not present.
     *                  But that will always result in a {@link BotCheckerResult#IS_NOT} answer.
     * @param ip The ip address of the client, eg "66.249.66.1"
     *           Be careful where you get this from, only read from http headers such as "X-Forwarded-For" if you
     *           trust the source, eg the right-most address if your network puts one it.
     *           See http://en.wikipedia.org/wiki/X-Forwarded-For
     * @return
     */
    @NotNull
    BotCheckerResult check(String userAgent, @NotNull String ip);

}
