package com.sujan.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Cost factor — 10 is production standard
    // Higher = more secure but slower
    private static final int COST = 10;

    // Private constructor — utility class, no instantiation needed
    private PasswordUtil() {}

    // ─────────────────────────────────────────────────────────
    // HASH — converts plain password to BCrypt hash
    // Called during REGISTER
    // ─────────────────────────────────────────────────────────
    public static String hash(String plainPassword) {
        // gensalt(COST) generates a random salt with cost factor
        // hashpw combines password + salt and hashes it
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(COST));
    }

    // ─────────────────────────────────────────────────────────
    // VERIFY — checks plain password against stored hash
    // Called during LOGIN
    // ─────────────────────────────────────────────────────────
    public static boolean verify(String plainPassword, String hashedPassword) {
        // BCrypt extracts salt from hash automatically
        // then hashes plainPassword with same salt
        // then compares results
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}