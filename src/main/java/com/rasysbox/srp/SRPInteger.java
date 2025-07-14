package com.rasysbox.srp;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

public class SRPInteger {

    private final BigInteger value;
    private final Integer hexLength;

    private SRPInteger(BigInteger value, Integer hexLength) {
        this.value = value;
        this.hexLength = hexLength;
    }

    /**
     * Create a SRPInteger from a hexadecimal string.
     *
     * @param input string hexadecimal to be converted.
     * @return an SRPInteger representing the hexadecimal value.
     */
    public static SRPInteger fromHex(String input) {
        String hex = input.replaceAll("[^0-9A-Fa-f]", "");
        if (hex.isEmpty()) throw new IllegalArgumentException("The string provided does not contain valid hex digits");
        return new SRPInteger(new BigInteger(hex, 16), hex.length());
    }

    /**
     * Generates a random SRPInteger of a specific length in bytes.
     *
     * @param bytes The length in bytes of the random number to generate.
     * @return an SRPInteger representing a random number of the specified length.
     */
    public static SRPInteger randomInteger(int bytes) {
        byte[] buf = new byte[bytes];
        ThreadLocalRandom.current().nextBytes(buf);
        return new SRPInteger(new BigInteger(1, buf), buf.length * 2);
    }

    /**
     * Creates a SRPInteger from another SRPInteger and an optional length.
     *
     * @param other the other SRPInteger to add.
     * @return an SRPInteger that represents the sum of this and the other.
     */
    public SRPInteger add(SRPInteger other) {
        return new SRPInteger(this.value.add(other.value), null);
    }

    /**
     * Multiplies this SRPInteger by another.
     *
     * @param other the other SRPInteger to multiply.
     * @return an SRPInteger that represents the product of this and the other.
     */
    public SRPInteger multiply(SRPInteger other) {
        return new SRPInteger(this.value.multiply(other.value), null);
    }

    /**
     * Calculates the modulus of this SRPInteger with another.
     *
     * @param m the SRPInteger to calculate the modulus with.
     * @return an SRPInteger that represents the result of the modulus.
     */
    public SRPInteger mod(SRPInteger m) {
        return new SRPInteger(this.value.mod(m.value), m.hexLength);
    }

    /**
     * Calculates the modular exponentiation of this SRPInteger.
     *
     * @param exp The exponent SRPInteger to raise this to.
     * @param m The modulus SRPInteger to apply.
     * @return an SRPInteger that represents the result of the modular exponentiation.
     */
    public SRPInteger modPow(SRPInteger exp, SRPInteger m) {
        return new SRPInteger(this.value.modPow(exp.value, m.value), m.hexLength);
    }

    /**
     * Subtracts another SRPInteger from this one.
     *
     * @param other The other SRPInteger to subtract.
     * @return an SRPInteger that represents the result of the subtraction.
     */
    public SRPInteger subtract(SRPInteger other) {
        return new SRPInteger(this.value.subtract(other.value), this.hexLength);
    }

    /**
     * Returns the bitwise XOR of this SRPInteger with another.
     *
     * @param other the other SRPInteger to perform the XOR operation with.
     * @return an SRPInteger that represents the result of the XOR operation.
     */
    public SRPInteger xor(SRPInteger other) {
        return new SRPInteger(this.value.xor(other.value), this.hexLength);
    }

    /**
     * Compares this SRPInteger with another for equality.
     *
     * @param other The other SRPInteger to compare with.
     * @return true if both SRPIntegers have the same value, false otherwise.
     */
    public boolean equals(SRPInteger other) {
        return this.value.equals(other.value);
    }

    /**
     * Converts this SRPInteger to a hexadecimal string representation.
     *
     * @return a chain of hexadecimal digits representing this SRPInteger.
     */
    public String toHex() {
        if (hexLength == null) {
            throw new IllegalStateException("This SRPInteger has no specified length");
        }
        String hex = value.toString(16);
        return "0".repeat(Math.max(0, hexLength - hex.length())) + hex;
    }

    public static final SRPInteger ZERO = new SRPInteger(BigInteger.ZERO, null);
}
