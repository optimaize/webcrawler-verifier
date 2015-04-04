package com.optimaize.webcrawlerverifier.dns;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.xbill.DNS.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Implementation that uses the dnsjava library.
 *
 * <p>If you have other needs, you may exclude the dnsjava Maven dependency using an exclude rule in your pom
 * and then feed your own implementation.</p>
 */
public class DnsjavaReverseDnsVerifier extends BaseReverseDnsVerifier {

    @Nullable
    private final String[] dnsServers;


    /**
     * Uses the default dns server(s) provided by the system.
     */
    public DnsjavaReverseDnsVerifier() {
        this.dnsServers = null;
    }

    /**
     * TODO verify the dns servers are used in both requests, see below.
     */
    public DnsjavaReverseDnsVerifier(@NotNull List<String> dnsServers) {
        this.dnsServers = ImmutableList.copyOf(dnsServers).toArray(new String[dnsServers.size()]);
    }



    @Override
    public boolean verify(@NotNull String ip, @NotNull Collection<String> allowedHostNames) throws IOException {
        InetAddress ipAsInetAddress = InetAddress.getByName(ip);
        byte[] bytes = ipAsInetAddress.getAddress();
        Optional<String> oActualHostname = getHostByAddr(bytes);
        if (!oActualHostname.isPresent()) {
            return false;
        }
        String actualHostname = oActualHostname.get();
        if (actualHostname.endsWith(".")) actualHostname = actualHostname.substring(0, actualHostname.length()-1);
        if (!contained(actualHostname, allowedHostNames)) {
            return false;
        }

        Optional<InetAddress> ipByHost = getIpByHost(actualHostname);
        if (!ipByHost.isPresent()) {
            return false;
        }
        if (!ipByHost.get().getHostAddress().equals(ip)) {
            return false;
        }

        return true;
    }


    /**
     * Performs a reverse DNS lookup.
     *
     * @param addr The ip address to lookup.
     * @return The host name found for the ip address.
     *         Absent if the ip is not mapped, or something.
     * @throws IOException on a possibly temporary network error.
     */
    private Optional<String> getHostByAddr(byte[] addr) throws IOException {
        Name name = ReverseMap.fromAddress(InetAddress.getByAddress(addr));

        Record[] records;
        Lookup lookup;
        if (dnsServers!=null) {
            final Resolver res = new ExtendedResolver(dnsServers);
            lookup = new Lookup(name, Type.PTR);
            lookup.setResolver(res);
            records = lookup.run();
        } else {
            lookup = new Lookup(name, Type.PTR);
            records = lookup.run();
        }

        int result = lookup.getResult();
        if (result==Lookup.TRY_AGAIN) {
            throw new IOException("Network error when trying to look up "+ Arrays.toString(addr) +", try again.");
        }
        if (result != Lookup.SUCCESSFUL || records == null) {
            return Optional.absent();
        }
        return Optional.of(((PTRRecord) records[0]).getTarget().toString());
    }

    /**
     * TODO how are timeouts handled? Why doesn't dnsjav document exceptions?
     * @return Absent for unknown host.
     */
    private static Optional<InetAddress> getIpByHost(String hostName) {
        //TODO do I need to use dnsServers here in case they are set?
        try {
            InetAddress addr = Address.getByName(hostName);
            return Optional.of(addr);
        } catch (UnknownHostException e) {
            return Optional.absent();
        }
    }



    private static boolean contained(String actualHostname, Collection<String> expectedDomain) {
        for (String s : expectedDomain) {
            if (actualHostname.equals(s)) return true;
            if (actualHostname.endsWith("." + s)) return true;
        }
        return false;
    }


}
