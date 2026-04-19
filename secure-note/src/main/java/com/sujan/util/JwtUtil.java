package com.sujan.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    // ─────────────────────────────────────────────────────────
    // SECRET KEY — used to sign and verify tokens
    // In production load from environment variable!
    // ─────────────────────────────────────────────────────────
    private static final String SECRET =
            EnvConfig.get("JWT_SECRET") != null
                    ? EnvConfig.get("JWT_SECRET")
                    : "thisisaverylongsecretkeythatisatleast256bitslongforhmacsecurity!";

    // Convert string secret to a cryptographic key
    private static final SecretKey KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    // Token expiry — 1 hour in milliseconds
    private static final long EXPIRY_MS = 60 * 60 * 1000;

    // Private constructor — utility class
    private JwtUtil() {}

    // ─────────────────────────────────────────────────────────
    // GENERATE TOKEN — called after successful login
    // Takes username and role → returns JWT string
    // ─────────────────────────────────────────────────────────
    public static String generateToken(String username, String role) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .subject(username)                        // who this token is for
                .claim("role", role)                      // extra data in payload
                .issuedAt(new Date(now))                  // when token was created
                .expiration(new Date(now + EXPIRY_MS))    // when token expires
                .signWith(KEY)                            // sign with secret key
                .compact();                               // build the string
    }

    // ─────────────────────────────────────────────────────────
    // VALIDATE TOKEN — called on every protected request
    // Returns claims if valid, throws exception if invalid
    // ─────────────────────────────────────────────────────────
    public static Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(KEY)          // use same key to verify signature
                .build()
                .parseSignedClaims(token) // parse and verify in one step
                .getPayload();            // return the payload data
    }

    // ─────────────────────────────────────────────────────────
    // GET USERNAME — extract username from token
    // ─────────────────────────────────────────────────────────
    public static String getUsername(String token) {
        return validateToken(token).getSubject();
    }

    // ─────────────────────────────────────────────────────────
    // GET ROLE — extract role from token
    // ─────────────────────────────────────────────────────────
    public static String getRole(String token) {
        return validateToken(token).get("role", String.class);
    }

    // ─────────────────────────────────────────────────────────
    // IS VALID — safe check without throwing exception
    // Returns true if valid, false if anything is wrong
    // ─────────────────────────────────────────────────────────
    public static boolean isValid(String token) {
        try {
            validateToken(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("❌ Token expired: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("❌ Invalid signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("❌ Malformed token: " + e.getMessage());
        } catch (JwtException e) {
            System.out.println("❌ JWT error: " + e.getMessage());
        }
        return false;
    }
}