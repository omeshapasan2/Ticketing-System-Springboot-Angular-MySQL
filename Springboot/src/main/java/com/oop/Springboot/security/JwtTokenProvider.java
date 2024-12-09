package com.oop.Springboot.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

/**
 * Utility class for handling JSON Web Token (JWT) generation.
 * This class provides methods to create a JWT token for authentication.
 */
public class JwtTokenProvider {

    // Secret key used for signing JWT tokens
    private static String secretKey = "pC6O3QyK95otTbR9vR7bYh1d5pNkF5R6YHvEkC2wgbI=";

    /**
     * Generates a JWT token with the given username and role.
     * The token contains the username as the subject, the role as a claim,
     * an issued date, and an expiration time of 24 hours from the current time.
     *
     * @param username the username of the authenticated user
     * @param role the role of the user (e.g., "ADMIN", "USER")
     * @return a signed JWT token as a String
     */
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
