package com.example.UserProfileManager3.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); //the key or secret
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days in milliseconds

    public static String generateToken(String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME); //determining the expiration date

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)//signing the key
                .compact();
    }

    public static Claims validateToken(String token) { //validate token
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims;
        } catch (Exception e) {
            return null;
        }
    }
}
