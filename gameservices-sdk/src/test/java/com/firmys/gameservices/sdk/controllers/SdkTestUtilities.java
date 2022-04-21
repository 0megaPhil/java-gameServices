package com.firmys.gameservices.sdk.controllers;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class SdkTestUtilities {
    public static class RandomString {

        /**
         * Generate a random string.
         */
        public String nextString() {
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = symbols[random.nextInt(symbols.length)];
            return new String(buf);
        }

        public int nextInt() {
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = symbols[random.nextInt(symbols.length)];
            return Integer.parseInt(new String(buf));
        }

        public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        public static final String lower = upper.toLowerCase(Locale.ROOT);

        public static final String digits = "0123456789";

        public static final String alpha = upper + lower;

        public static final String alphaNum = upper + lower + digits;

        private final Random random;

        private final char[] symbols;

        private final char[] buf;

        public RandomString(int length, Random random, String symbols) {
            if (length < 1) throw new IllegalArgumentException();
            if (symbols.length() < 2) throw new IllegalArgumentException();
            this.random = Objects.requireNonNull(random);
            this.symbols = symbols.toCharArray();
            this.buf = new char[length];
        }

        /**
         * Create an alphanumeric string generator.
         */
        public RandomString(int length, Random random) {
            this(length, random, alphaNum);
        }

        public static String getAlphaNumeric() {
            Random random = new Random();
            return new RandomString(random.nextInt(1, 300), random, alphaNum).nextString();
        }

        public static String getAlpha() {
            Random random = new Random();
            return new RandomString(random.nextInt(1, 300), random, alpha).nextString();
        }

        public static int getNumeric() {
            Random random = new Random();
            return new RandomString(random.nextInt(1, 9), random, digits).nextInt();
        }

        public static double getDouble() {
            return 1 + new Random().nextDouble() * (-100);
        }

        /**
         * Create an alphanumeric strings from a secure generator.
         */
        public RandomString(int length) {
            this(length, new SecureRandom());
        }

        /**
         * Create session identifiers.
         */
        public RandomString() {
            this(21);
        }

    }
}
