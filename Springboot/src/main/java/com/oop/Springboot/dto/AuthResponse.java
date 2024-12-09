package com.oop.Springboot.dto;

/**
 * AuthResponse is a Data Transfer Object (DTO) that represents the authentication response.
 * It contains the authentication token and the role of the authenticated user.
 */
public class AuthResponse {

    private String token;
    private String role;

    /**
     * Constructs an AuthResponse with the specified token and role.
     *
     * @param token the authentication token
     * @param role the role of the authenticated user
     */
    public AuthResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }

    /**
     * Retrieves the authentication token.
     *
     * @return the authentication token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the authentication token.
     *
     * @param token the authentication token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Retrieves the role of the authenticated user.
     *
     * @return the role of the authenticated user
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the authenticated user.
     *
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }
}
