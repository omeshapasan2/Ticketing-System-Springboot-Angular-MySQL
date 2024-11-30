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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginUser) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
        );

        // If authentication is successful, set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get the authenticated user
        String currentUsername = authentication.getName();
        Optional<User> user = userService.findByUsername(currentUsername);

        if (user.isPresent()) {
            // Here you can create a response with the token and role
            String role = user.get().getRole();
            String token = JwtTokenProvider.generateToken(currentUsername, role); // Assuming you have a method to generate token

            return ResponseEntity.ok(new AuthResponse(token, role)); // AuthResponse is a DTO containing token and role
        }

        return ResponseEntity.badRequest().build();
    }
}
