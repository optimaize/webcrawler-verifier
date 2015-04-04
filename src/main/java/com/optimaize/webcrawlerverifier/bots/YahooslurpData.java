package com.optimaize.webcrawlerverifier.bots;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

/**
 * For the former Yahoo bot. Now they get their data from Bing.
 * Now I'm not sure if they still fire requests. It was announced to be taken offline. I guess this means
 * that Slurp does not need to be supported (detected) anymore.
 *
 * Resources:
 * http://en.wikipedia.org/wiki/Yahoo!_Slurp
 * https://help.yahoo.com/kb/search/slurp-crawling-page-sln22600.html
 * http://webmasters.stackexchange.com/questions/22565/is-there-any-reason-to-allow-yahoo-slurp-to-crawl-my-site
 */
public class YahooslurpData implements CrawlerData {

    private static final Predicate<String> PREDICATE = new Predicate<String>() {
        @Override
        public boolean apply(String userAgent) {
            //see http://en.wikipedia.org/wiki/Yahoo!_Slurp
            return userAgent.contains("Yahoo! Slurp") || userAgent.contains("Yahoo Slurp");
        }
    };

    private static final ImmutableSet<String> HOSTNAMES = ImmutableSet.of(
            //They also recommend the reverse and forward DNS verification method:
            //http://www.ysearchblog.com/2007/06/05/yahoo-search-crawler-slurp-has-a-new-address-and-signature-card/
            //in 2007 they moved from inktomisearch.com to crawl.yahoo.net
            "crawl.yahoo.net" //this used to be the one before switching to bing.

            //many ip's now go to yahoo.com, things like "UNKNOWN-8-12-144-X.yahoo.com"
            //but they don't resolve back. so we can't add that.
    );


    private static final YahooslurpData INSTANCE = new YahooslurpData();
    public static YahooslurpData getInstance() {
        return INSTANCE;
    }
    private YahooslurpData() {
    }


    @NotNull
    @Override
    public String getIdentifier() {
        return "YAHOOSLURP";
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
