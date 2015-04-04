package com.optimaize.webcrawlerverifier.dns;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 * Base class implementing one of the methods by calling the other.
 */
public abstract class BaseReverseDnsVerifier implements ReverseDnsVerifier {

    @Override
    public boolean verify(@NotNull String ip, @NotNull String allowedHostName) throws IOException {
        return verify(ip, Collections.singleton(allowedHostName));
    }

    @Override
    public boolean verify(@NotNull String ip, @NotNull Collection<String> allowedHostNames) throws IOException {
        return false;
    }
}
