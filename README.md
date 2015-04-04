# webcrawler-verifier

Webcralwer-Verifier is a Java library to ensure that robots are from the operator they claim to be,
eg that Googlebot is actually coming from Google and not from some spoofer.


## Maven

The library will be published to Maven Central shortly.


## How to use

        List<KnownHostBotVerifier> verifiers = new ArrayList<>();
        for (CrawlerData crawlerData : BuiltInCrawlers.get()) {
            verifiers.add(new KnownHostBotVerifierBuilder()
                    .crawlerData(crawlerData)
                    .dnsVerifierDefault()
                    .dnsResultCacheDefault()
                    .build());
        }
        DefaultKnownCrawlerDetector detector = new DefaultKnownCrawlerDetector(verifiers);

        String userAgent = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
        String clientIp  = "66.249.66.1";
        Optional<KnownCrawlerResult> result = detector.detect(userAgent, clientIp);


## Built in crawler detection

1. Google: Googlebot
1. Bing: Bingbot
1. Baidu: Baidu spider
1. Yandex: Yandexbot
1. DuckDuckGo: DuckDuckBot
1. Yahoo: Yahoo Slurp (end of life, uses Bingbot now)

Contributions are welcome.
Sogou (Sogouspider), archive.org, and more would be nice.


## Why, use cases

Some webmasters are concerned about unnecessary web traffic that bring no benefit to their sites (no visitors).
Others care about privacy and data scraping.

There are good crawlers, like Googlebot, that reward you for the load they cause.
Others, like Baidu, may be a waste of your resources, depending on your target audience.
Yet others are totally useless to the regular webmaster and should be blocked.

Blocking bots is easy if they identify nicely. Use the robots.txt http://en.wikipedia.org/wiki/Robots_exclusion_standard

Some scrapers avoid a block or traffic and speed limits by pretending to be an important crawler.
That's where this library comes in. There's no magic, you can also roll your own. It's just nice to
be able to pull in a Maven dependency that is kept up to date, and move on to core business tasks.


## How to support a new crawler

1. Implement the CrawlerData interface.
   If the service provider adheres to a specific host name, do it like GooglebotData.
   If not, and you identify by known ip addresses, do it like DuckduckbotData.
   Be sure to document the sources (user-agent, hostname, ips) in that file, just like the others.
1. Add it to BuiltInCrawlers, or else, if you don't contribute, then add it to your own collection class.
1. Test it. Add a case to DefaultKnownCrawlerDetectorTest following the logic of the others.


## How it works

#### Step one is identification.

If the user-agent identifies as one of the bots you are checking for, it goes into step 2 for verification.
If not, none is reported.

#### Step two is verification.

The robot that was reported in the user-agent is verified by looking at the client's network address.
The big ones work with a combination of dns + reverse-dns lookup. That's not a hack, it's the officially
recommended way. The ip resolves to a hostname of the provider, and the hostname has a reverse dns entry
pointing back to that ip. This gives the crawler operators the freedom to to change and add networks
without risking of being locked out of websites.

The other method is to maintain lists of ip addresses. This is used for those operators that don't
officially endorse the first method. And it can optionally be used in combination with the first method
to avoid the one-time cost of the dns verification.

Except where it's required (for the 2nd method) this project does not maintain ip lists. The ones that
can currently be found on the internet all seem outdated. And that's exactly the problem... they will
always be lagging behind the ip ranges that the operators use.


## Configuration options

You can choose which built-in and which of your own crawlers you with to detect.

The Guava cache is configurable, you can pass in your own. Especially the expiration time is of interest.
The cache size is not, there should never be too many distinct IPs that identify as a certain bot.

The DNS verification uses the system's default server(s), and can be configured to use others. See
open tasks. It's probably also possible to configure timeouts.

Logging is done using the log4j api, you choose the implementation (logback is a solid choice).
Currently there's hardly any logging performed.


## Project status

The project is brand new. The code is tested, the spiders are tested, it's all working. Still, only time
and multiple users will make it a solid piece of work.


## Demarcation

This library does not perform user-agent string parsing. It does not (and shall not) have deeper knowledge
of the crawlers, such as

- whether they are popular or not
- whether they are from search engines like Google and Bing, and therefore may drive traffic to your site,
  or from special purpose sites like archive.org, or from bandwidth wasters that have no value to you
- whether they spider html text content or images
- for which country a spider is, eg Baidu China vs Japan

For such kind of information take a look at http://uadetector.sourceforge.net/


## External dependencies

(see pom.xml file)

1. com.intellij annotations - For the @Nullable and @NotNull annotations. It's tiny.
1. Guava - For the Optional and the Cache. No discussion.
1. org.slf4j slf4j-api - The logging api everyone uses.
1. dnsjava - For the dns lookups. Alternatively you can put an exclude filter on it and implement the
   ReverseDnsVerifier interface yourself.


## Terminology

- Crawler, web crawler, bot and robot are used as synonyms.


## Resources

#### Other projects of interest

- User agent detector library http://uadetector.sourceforge.net/

#### Interesting reads

- Dan Birken: Who exactly is crawling my site? http://danbirken.com/seo/2013/10/17/who-exactly-is-crawling-my-site.html

## Search engines

- How to identify and verify Googelbot: https://support.google.com/webmasters/answer/80553
- How to identify and verify Bingbot: http://www.bing.com/webmaster/help/how-to-verify-bingbot-3905dc26
