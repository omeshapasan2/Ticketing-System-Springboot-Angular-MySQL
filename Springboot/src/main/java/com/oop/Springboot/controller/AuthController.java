package com.oop.Springboot.controller;

import com.oop.Springboot.dto.AuthResponse;
import com.oop.Springboot.security.JwtTokenProvider;
import com.oop.Springboot.entity.User;
import com.oop.Springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller class responsible for handling authentication-related endpoints
 * such as user registration and login.
 */
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from the specified frontend origin (typically Angular frontend)
@RestController
@RequestMapping("/auth") // Base URL for all authentication-related endpoints
public class AuthController {

    private final UserService userService; // Service to handle user-related operations
    private final AuthenticationManager authenticationManager; // Manager to authenticate users

    /**
     * Constructs a new AuthController with the given userService and authenticationManager.
     *
     * @param userService the service to handle user-related operations
     * @param authenticationManager the manager to authenticate users
     */
    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Endpoint to register a new user.
     * It checks if the username already exists. If it does, returns a 400 Bad Request.
     * If the username is available, it saves the user to the database and returns the saved user details.
     *
     * @param user the user details to register
     * @return ResponseEntity with the registered user or 400 Bad Request if username exists
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // Check if the username already exists
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            // If the username exists, return 400 Bad Request
            return ResponseEntity.badRequest().build();
        }
        // Save the user to the database and return the saved user details
        return ResponseEntity.ok(userService.saveUser(user));
    }

    /**
     * Endpoint to login an existing user.
     * It authenticates the user using the provided username and password.
     * If authentication is successful, a JWT token is generated and returned with the user's role.
     * If authentication fails, a 400 Bad Request is returned.
     *
     * @param loginUser the user credentials to login
     * @return ResponseEntity containing the JWT token and role if login is successful, or 400 Bad Request if authentication fails
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginUser) {
        // Attempt to authenticate the user with the provided username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
        );

        // If authentication is successful, set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get the authenticated username
        String currentUsername = authentication.getName();

        // Find the user by the authenticated username
        Optional<User> user = userService.findByUsername(currentUsername);

        if (user.isPresent()) {
            // If the user exists, create a JWT token and include the user's role in the response
            String role = user.get().getRole(); // Get the role of the user
            String token = JwtTokenProvider.generateToken(currentUsername, role); // Generate the token

            // Return the token and role in the response
            return ResponseEntity.ok(new AuthResponse(token, role));
        }

        // If authentication fails (user not found), return 400 Bad Request
        return ResponseEntity.badRequest().build();
    }
}
