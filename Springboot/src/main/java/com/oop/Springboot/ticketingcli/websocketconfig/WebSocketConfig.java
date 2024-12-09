package com.oop.Springboot.ticketingcli.websocketconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * WebSocket configuration class to enable and configure WebSocket handlers.
 * This configuration class registers WebSocket handlers for handling specific
 * WebSocket routes, such as the logs WebSocket route.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    /**
     * Registers WebSocket handlers with specific WebSocket routes.
     * This method configures the WebSocket handler for the logs route
     * and applies an HTTP session handshake interceptor.
     *
     * @param registry the registry to add WebSocket handlers
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Add a WebSocket handler for handling log-related communication
        registry.addHandler(new LogWebSocketHandler(), "/logs")
                .addInterceptors(new HttpSessionHandshakeInterceptor())  // Intercept the handshake to manage sessions
                .setAllowedOrigins("*");  // Allow connections from any origin (can be more restricted)
    }
}
