package com.sujan.spring_secu_demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

//    private static final String SECRET = "bMSBYaGWHZJSIUooo1LAOq/sSraQwjSvVbhnNku6j8BY7PERfBeEpJ45+HbaE9hkVoaE7tpU4O7suWtcqkAE5Q==";
    private final String secret;

    public JwtService() {
        this.secret = System.getenv("JWT_SECRET");
        System.out.println("JWT_SECRET: " + secret);
        if (this.secret == null) {
            throw new IllegalStateException("JWT_SECRET environment variable not set");
        }
    }
    /**
     *
     *SECRET algorithm type auto infrared based on byte length if 512>= then HmacSHA512 , if 384>= HmacSHA384
     *  if 256 >= HmacSHA256
     */
    public String generateToken(String username){
        Map<String,Object> claims = new HashMap<>();

        return  Jwts.builder()
                .claims(claims) // claims means what jwt contains about user in payload
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*10))
                .signWith(getKey())
                .compact();
    }


    public SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T>T extractClaims(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /*private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getKey())
                .build()
                .parseClaimsJws(token).getBody();
    }*/
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
