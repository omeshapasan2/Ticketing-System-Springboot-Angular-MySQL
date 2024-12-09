package com.oop.Springboot.config;

import com.oop.Springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    /**
     * Constructor for SecurityConfig class.
     * Injects the UserService instance used for authentication.
     *
     * @param userService the user service for loading user details
     */
    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    /**
     * Configures HTTP security for the application.
     * - Disables CSRF (commonly used for APIs)
     * - Configures authorization rules (public access to certain paths)
     * - Disables the default form login
     * - Enables CORS
     *
     * @param http the HttpSecurity object for configuration
     * @return SecurityFilterChain configured security filter chain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()  // Allow login
                        .requestMatchers("/api/ticketing/config", "/api/ticketing/logs", "/logs", "/api/ticketing/stop", "/api/ticketing-status", "/ws/**", "/ticket-progress").permitAll()  // Allow dashboard logging and websocket
                        .anyRequest().authenticated()  // Require authentication for all other requests
                )
                .formLogin().disable()  // Disable form login
                .cors();  // Enable CORS

        return http.build();
    }

    /**
     * Configures the AuthenticationManager with custom UserService and password encoder.
     *
     * @param http the HttpSecurity object for configuration
     * @return AuthenticationManager configured authentication manager
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userService)  // Use custom UserService for authentication
                .passwordEncoder(passwordEncoder());  // Use BCryptPasswordEncoder for password encoding
        return authenticationManagerBuilder.build();
    }

    /**
     * Configures the password encoder to use BCrypt.
     *
     * @return PasswordEncoder configured BCryptPasswordEncoder
     */
    @Bean
    @Lazy
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures Cross-Origin Resource Sharing (CORS) for the application.
     * Allows specific origins and HTTP methods for cross-origin requests.
     *
     * @return CorsFilter configured CORS filter
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);  // Allow credentials (cookies, HTTP headers, etc.)
        corsConfig.addAllowedOrigin("http://localhost:4200");  // Allow requests from localhost:4200 (common for frontend in Angular)
        corsConfig.addAllowedHeader("*");  // Allow all headers
        corsConfig.addAllowedMethod("*");  // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
        corsConfig.addExposedHeader("Authorization");  // Expose Authorization header

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);  // Apply the CORS configuration to all endpoints

        return new CorsFilter(source);  // Return the CORS filter
    }
}
