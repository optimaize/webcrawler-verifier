package com.optimaize.webcrawlerverifier;

import com.optimaize.webcrawlerverifier.bots.*;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

/**
 * These are not unit tests, they are real world data tests. They may fail if
 *  - a provider has changed their network setup
 *  - internet drops
 */
public class DefaultKnownCrawlerDetectorTest {

    @Test
    public void none() throws Exception {
        DefaultKnownCrawlerDetector detector = all();
        assertFalse(detector.detect("", "127.0.0.1").isPresent());
    }

    /**
     * see https://support.google.com/webmasters/answer/1061943?hl=en
     */
    @Test
    public void googlebot() throws Exception {
        DefaultKnownCrawlerDetector detector = all();

        KnownCrawlerResult r = new KnownCrawlerResult("GOOGLEBOT", KnownCrawlerResultStatus.VERIFIED);
        assertEquals(detector.detect("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)", "66.249.66.1").get(), r);
        assertEquals(detector.detect("Googlebot/2.1 (+http://www.google.com/bot.html)", "66.249.66.1").get(), r);
        assertEquals(detector.detect("Googlebot-News", "66.249.66.1").get(), r);
        assertEquals(detector.detect("Googlebot-Image/1.0", "66.249.66.1").get(), r);
        assertEquals(detector.detect("Googlebot-Video/1.0", "66.249.66.1").get(), r);
        assertEquals(detector.detect("Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5376e Safari/8536.25 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)", "66.249.66.1").get(), r);

        //disputable. not Googlebot strictly speaking, currently the string is not matched.
        assertFalse(detector.detect("Mediapartners-Google", "66.249.66.1").isPresent());
        assertFalse(detector.detect("AdsBot-Google (+http://www.google.com/adsbot.html)", "66.249.66.1").isPresent());

        //failing by ip:
        r = new KnownCrawlerResult("GOOGLEBOT", KnownCrawlerResultStatus.IMPERSONATOR);
        assertEquals(detector.detect("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)", "55.55.55.55").get(), r);
    }

    /**
     * see http://www.bing.com/webmaster/help/which-crawlers-does-bing-use-8c184ec0
     */
    @Test
    public void bingbot() throws Exception {
        DefaultKnownCrawlerDetector detector = all();

        KnownCrawlerResult r = new KnownCrawlerResult("BINGBOT", KnownCrawlerResultStatus.VERIFIED);
        assertEquals(detector.detect("Mozilla/5.0 (compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm)", "157.55.33.18").get(), r);
        assertEquals(detector.detect("msnbot/2.0b (+http://search.msn.com/msnbot.htm)", "157.55.33.18").get(), r);
        assertEquals(detector.detect("msnbot-media/1.1 (+http://search.msn.com/msnbot.htm)", "157.55.33.18").get(), r);
        assertEquals(detector.detect("adidxbot/1.1 (+http://search.msn.com/msnbot.htm)", "157.55.33.18").get(), r);

        //disputable. currently string is not matched.
        assertFalse(detector.detect("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534+ (KHTML, like Gecko) BingPreview/1.0b", "157.55.33.18").isPresent());
        assertFalse(detector.detect("Mozilla/5.0 (Windows Phone 8.1; ARM; Trident/7.0; Touch; rv:11.0; IEMobile/11.0; NOKIA; Lumia 530) like Gecko BingPreview/1.0b", "157.55.33.18").isPresent());

        //failing by ip:
        r = new KnownCrawlerResult("BINGBOT", KnownCrawlerResultStatus.IMPERSONATOR);
        assertEquals(detector.detect("Mozilla/5.0 (compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm)", "55.55.55.55").get(), r);
    }

    @Test
    public void baiduspider() throws Exception {
        DefaultKnownCrawlerDetector detector = all();

        KnownCrawlerResult r = new KnownCrawlerResult("BAIDUSPIDER", KnownCrawlerResultStatus.VERIFIED);
        assertEquals(detector.detect("Baiduspider+(+http://www.baidu.com/search/spider.htm)", "123.125.66.120").get(), r);
        assertEquals(detector.detect("Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html)", "123.125.66.120").get(), r);

        //failing by ip:
        r = new KnownCrawlerResult("BAIDUSPIDER", KnownCrawlerResultStatus.IMPERSONATOR);
        assertEquals(detector.detect("Baiduspider+(+http://www.baidu.com/search/spider.htm)", "55.55.55.55").get(), r);
    }

    @Test
    public void yandexbot() throws Exception {
        DefaultKnownCrawlerDetector detector = all();

        KnownCrawlerResult r = new KnownCrawlerResult("YANDEXBOT", KnownCrawlerResultStatus.VERIFIED);
        assertEquals(detector.detect("Mozilla/5.0 (compatible; YandexDirect/3.0; +http://yandex.com/bots)", "141.8.189.111").get(), r);

        //failing by ip:
        r = new KnownCrawlerResult("YANDEXBOT", KnownCrawlerResultStatus.IMPERSONATOR);
        assertEquals(detector.detect("Mozilla/5.0 (compatible; YandexDirect/3.0; +http://yandex.com/bots)", "55.55.55.55").get(), r);
    }

//    @Test
//    public void sogouspider() throws Exception {
//        DefaultKnownCrawlerDetector detector = all();
//
//        KnownCrawlerResult r = new KnownCrawlerResult("SOGOUSPIDER", KnownCrawlerResultStatus.VERIFIED);
//        assertEquals(detector.detect("Sogou web spider/4.0(+http://www.sogou.com/docs/help/webmasters.htm#07)", "220.181.94.212").get(), r);
//
//        //failing by ip:
//        r = new KnownCrawlerResult("SOGOUSPIDER", KnownCrawlerResultStatus.IMPERSONATOR);
//        assertEquals(detector.detect("Sogou web spider/4.0(+http://www.sogou.com/docs/help/webmasters.htm#07)", "55.55.55.55").get(), r);
//    }

    @Test
    public void duckduckbot() throws Exception {
        DefaultKnownCrawlerDetector detector = all();

        KnownCrawlerResult r = new KnownCrawlerResult("DUCKDUCKBOT", KnownCrawlerResultStatus.VERIFIED);
        assertEquals(detector.detect("DuckDuckBot/1.0; (+http://duckduckgo.com/duckduckbot.html)", "72.94.249.35").get(), r);

        //failing by ip:
        r = new KnownCrawlerResult("DUCKDUCKBOT", KnownCrawlerResultStatus.IMPERSONATOR);
        assertEquals(detector.detect("DuckDuckBot/1.0; (+http://duckduckgo.com/duckduckbot.html)", "55.55.55.55").get(), r);
    }

    @Test
    public void yahooslurp() throws Exception {
        DefaultKnownCrawlerDetector detector = all();

        KnownCrawlerResult r;

        //i have no current ip from them... can't test. the ones published on various sites are outdated.
        //r = new KnownCrawlerResult("YAHOOSLURP", KnownCrawlerResultStatus.VERIFIED);
        //assertEquals(detector.detect("Mozilla/5.0 (compatible; Yahoo! Slurp; http://help.yahoo.com/help/us/ysearch/slurp)", "put ip here").get(), r);

        //failing by ip:
        r = new KnownCrawlerResult("YAHOOSLURP", KnownCrawlerResultStatus.IMPERSONATOR);
        assertEquals(detector.detect("Mozilla/5.0 (compatible; Yahoo! Slurp; http://help.yahoo.com/help/us/ysearch/slurp)", "55.55.55.55").get(), r);
    }


    private DefaultKnownCrawlerDetector all() {
        List<KnownHostBotVerifier> verifiers = new ArrayList<>();
        for (CrawlerData crawlerData : BuiltInCrawlers.get()) {
            verifiers.add(new KnownHostBotVerifierBuilder()
                    .crawlerData(crawlerData)
                    .dnsVerifierDefault()
                    .dnsResultCacheDefault()
                    .build());
        }
        return new DefaultKnownCrawlerDetector(verifiers);
    }

}