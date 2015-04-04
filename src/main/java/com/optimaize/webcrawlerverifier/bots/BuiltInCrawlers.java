package com.optimaize.webcrawlerverifier.bots;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Provides access to the built-in crawler data.
 */
public class BuiltInCrawlers {

    /**
     * The list is returned non-strictly defined order of global importance.
     * This way, the more important ones are checked first when iterating.
     *
     * <p>Don't want all? Filter on {@link CrawlerData#getIdentifier()}.</p>
     */
    @NotNull
    public static List<CrawlerData> get() {
        return ImmutableList.of(
                //currently Bing seems to be spidering the most, but still...
                GooglebotData.getInstance(),
                BingbotData.getInstance(),

                //Baidu spiders a lot too.
                BaiduspiderData.getInstance(),

                //i don't know about these...
                YandexbotData.getInstance(),
                //SogouspiderData.getInstance(),

                //here come the unimportant ones.
                DuckduckbotData.getInstance(),

                //Slurp is end-of-life, can probably be removed soon.
                YahooslurpData.getInstance()
        );
    }

}
