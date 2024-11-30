package com.oop.Springboot.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JwtTokenProvider {

    private static String secretKey = "C6O3QyK95otTbR9vR7bYh1d5pNkF5R6YHvEpkC2wgbI="; // Replace with a strong secret key

    public static String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
