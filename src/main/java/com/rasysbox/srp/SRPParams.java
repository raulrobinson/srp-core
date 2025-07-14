package com.rasysbox.srp;

import java.security.NoSuchAlgorithmException;

public class SRPParams {

    /**
     * The N parameter is a large prime number used in the SRP protocol.
     */
    private static final String RAW_N = (
            "AC6BDB41324A9A9BF166DE5E1389582FAF72B6651987EE07FC3192943DB56050A" +
                    "A37329CBB4A099ED8193E0757767A13DD52312AB4B03310DCD7F48A9DA04FD50E" +
                    "8083969EDB767B0CF6095179A163AB3661A05FB D5FAAAE82918A9962F0B93B855" +
                    "F97993EC975EEAA80D740ADBF4FF747359D041D5C33EA71D281E446B14773BCA" +
                    "97B43A23FB801676BD207A436C6481F1D2B9078717461A5B9D32E688F8774854" +
                    "4523B524B0D57D5EA77A2775D2ECFA032CFBDBF52FB3786160279004E57AE6AF" +
                    "874E7303CE53299CCC041C7BC308D82A5698F3A8D0C38271AE35F8E9DBFBB694B" +
                    "5C803D89F7AE435DE236D525F54759B65E372FCD68EF20FA7111F9E4AFF73"
    ).replaceAll("\\s+", "");

    /**
     * The N parameter is a large prime number used in the SRP protocol.
     */
    public static final SRPInteger N = SRPInteger.fromHex(RAW_N);

    /**
     * The g parameter is a generator used in the SRP protocol.
     */
    public static final SRPInteger g = SRPInteger.fromHex("02");

    /**
     * H_N is the hash of the N parameter.
     */
    public static final SRPInteger H_N;

    /**
     * H_g is the hash of the g parameter.
     */
    public static final SRPInteger H_g;

    /**
     * k is the multiplier used in the SRP protocol, calculated as H(N, g).
     */
    public static final SRPInteger k;

    /**
     * The number of bytes used for hash output in the SRP protocol.
     */
    public static final int HASH_OUTPUT_BYTES = 256 / 8;

    static {
        try {
            H_N = SRPUtils.H(N);
            H_g = SRPUtils.H(g);
            k   = SRPUtils.H(N, g);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error inicializando SRPParams", e);
        }
    }

}
