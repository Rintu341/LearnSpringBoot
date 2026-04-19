package com.sujan.util;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()  // don't crash if .env missing
            .load();

    private EnvConfig() {}

    // Reads from .env file first, falls back to system env
    public static String get(String key) {
        String value = dotenv.get(key);
        if (value == null) {
            value = System.getenv(key);
        }
        return value;
    }
}