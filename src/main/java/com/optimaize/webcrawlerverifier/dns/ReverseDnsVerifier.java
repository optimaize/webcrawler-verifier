package com.optimaize.webcrawlerverifier.dns;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collection;

/**
 *
 */
public interface ReverseDnsVerifier {

    /**
     * Verifies that the given ip resolves to the given host name, and that the host name has a reverse dns
     * pointing back to the ip.
     *
     * <p>Example: if checking for "foo.example.com" then it allows "foo.example.com" and "anything.foo.example.com",
     * but not "example.com" nor "foofoo.example.com".</p>
     *
     * @param ip eg "127.0.0.1"
     * @param allowedHostName eg "example.com" lower case, without trailing dot.
     * @throws IOException in case of a dns lookup error (temporary network errors).
     *         Not if the ip misses hostname or reverse dns configurations.
     */
    public boolean verify(@NotNull String ip, @NotNull String allowedHostName) throws IOException;

    /**
     * Overloaded method that allows multiple domains.
     * @see #verify(String, String)
     */
    public boolean verify(@NotNull String ip, @NotNull Collection<String> allowedHostNames) throws IOException;

}
