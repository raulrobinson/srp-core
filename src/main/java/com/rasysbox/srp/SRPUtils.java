package com.rasysbox.srp;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static ch.qos.logback.core.encoder.ByteArrayUtil.hexStringToByteArray;

public class SRPUtils {

    /**
     * Calculate the SHA-256 hash of the provided arguments.
     * Accepts both SRPInteger and String types.
     *
     * @param args Arguments to be hashed
     * @return a SRPInteger representing the SHA-256 hash
     * @throws NoSuchAlgorithmException if SHA-256 is not available
     */
    public static SRPInteger H(Object... args) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        for (Object arg : args) {
            if (arg instanceof SRPInteger si) {
                byte[] bin = hexStringToByteArray(si.toHex());
                md.update(bin);
            } else if (arg instanceof String s) {
                md.update(s.getBytes(StandardCharsets.UTF_8));
            } else {
                throw new IllegalArgumentException("H() only accept SRPInteger or String");
            }
        }
        String hex = bytesToHex(md.digest());
        return SRPInteger.fromHex(hex);
    }

    /**
     * Calculates the SHA-256 hash of a String value.
     *
     * @param hex value to be hashed
     * @return a SRPInteger representing the SHA-256 hash of the value
     */
    private static byte[] hexToBytes(String hex) {
        byte[] b = new byte[hex.length()/2];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) Integer.parseInt(hex.substring(2*i,2*i+2),16);
        }
        return b;
    }

    /**
     * Converts a byte array to a hexadecimal representation.
     *
     * @param buf array of bytes to convert
     * @return a chain representing the bytes in hexadecimal format
     */
    private static String bytesToHex(byte[] buf) {
        var sb = new StringBuilder();
        for (byte by : buf) sb.append(String.format("%02x", by));
        return sb.toString();
    }

    /**
     * Generate a random salt of 256 bits (32 bytes) in hex format.
     *
     * @return a chain representing the random salt in hexadecimal format
     */
    public static String generateSalt() {
        SRPInteger salt = SRPInteger.randomInteger(SRPParams.HASH_OUTPUT_BYTES);
        return salt.toHex();
    }

}
