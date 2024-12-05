package com.oop.Springboot.ticketingcli.websocketconfig;

import com.oop.Springboot.ticketingcli.LogManager;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class LogWebSocketHandler extends TextWebSocketHandler {

    // When a new WebSocket connection is established
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Remove the session from the LogManager when it disconnects
        LogManager.removeSession(session);
        System.out.println("WebSocket session closed: " + session.getId());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Add the new session to the LogManager so it can send messages to this client
        LogManager.addSession(session);
        System.out.println("WebSocket session established: " + session.getId());

        // Optionally send an initial message to the client
        String jsonMessage = "{\"log\": \"Connected to the log stream.\"}";
        session.sendMessage(new TextMessage(jsonMessage));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming messages (if needed, though for logs this may not be required)
        System.out.println("Received message from client: " + message.getPayload());
    }
}
