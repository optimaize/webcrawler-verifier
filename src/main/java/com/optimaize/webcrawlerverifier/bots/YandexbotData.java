package com.optimaize.webcrawlerverifier.bots;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

/**
 * Resources:
 * http://yandex.com/bots
 * http://help.yandex.com/search/robots/check-robot.xml#check-robot
 * http://searchenginewatch.com/sew/news/2067357/bye-bye-crawler-blocking-parasites
 */
public class YandexbotData implements CrawlerData {

    private static final Predicate<String> PREDICATE = new Predicate<String>() {
        @Override
        public boolean apply(String userAgent) {
            if (userAgent.contains("Yandex")) return true;
            return false;
        }
    };

    //exactly as documented by yandex:
    private static final ImmutableSet<String> HOSTNAMES = ImmutableSet.of("yandex.ru", "yandex.net", "yandex.com");


    private static final YandexbotData INSTANCE = new YandexbotData();
    public static YandexbotData getInstance() {
        return INSTANCE;
    }
    private YandexbotData() {
    }


    @NotNull
    @Override
    public String getIdentifier() {
        return "YANDEXBOT";
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
