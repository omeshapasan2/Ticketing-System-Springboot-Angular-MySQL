package com.oop.Springboot.ticketingcli.statuswebsocketconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Configuration class for WebSocket handling related to ticket progress updates.
 * This class configures the WebSocket handler and enables WebSocket communication
 * for the /ticket-progress endpoint.
 */
@Configuration
@EnableWebSocket
public class StatusWebSocketConfig implements WebSocketConfigurer {

    /**
     * Registers the WebSocket handler for the /ticket-progress endpoint.
     * It also adds an interceptor to manage session handshakes.
     *
     * @param registry the WebSocketHandlerRegistry used to register handlers
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(ticketProgressHandler(), "/ticket-progress")
                .addInterceptors(new HttpSessionHandshakeInterceptor())  // Interceptor to handle session handshake
                .setAllowedOrigins("*");  // Allow all origins
    }

    /**
     * Creates and returns a new instance of the WebSocket handler for ticket progress.
     *
     * @return the WebSocketHandler for ticket progress
     */
    @Bean
    public WebSocketHandler ticketProgressHandler() {
        return new TicketProgressHandler();  // Return the custom handler for ticket progress updates
    }
}
