package com.oop.Springboot.ticketingcli.websocketconfig;

import com.oop.Springboot.ticketingcli.LogManager;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * WebSocket handler for managing log-related communication with WebSocket clients.
 * This handler handles the connection lifecycle, including connection establishment, disconnection,
 * and message handling, and is responsible for sending and receiving log messages.
 */
public class LogWebSocketHandler extends TextWebSocketHandler {

    /**
     * Called after the WebSocket connection has been closed.
     * This method removes the WebSocket session from the LogManager.
     *
     * @param session the WebSocket session that was closed
     * @param status the status of the WebSocket closure
     * @throws Exception if an error occurs during disconnection handling
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Remove the session from the LogManager when disconnected
        LogManager.removeSession(session);
        System.out.println("WebSocket session closed: " + session.getId());
    }

    /**
     * Called after the WebSocket connection has been established.
     * This method adds the WebSocket session to the LogManager and sends an initial log message
     * indicating the successful connection.
     *
     * @param session the WebSocket session that was established
     * @throws Exception if an error occurs during connection establishment
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Add the session to the LogManager to send messages
        LogManager.addSession(session);
        System.out.println("WebSocket session established: " + session.getId());

        // Send an initial message to the client
        String jsonMessage = "{\"log\": \"Connected to the log stream.\"}";
        session.sendMessage(new TextMessage(jsonMessage));
    }

    /**
     * Handles incoming text messages sent by the WebSocket client.
     * This method simply prints the received message to the console for logging purposes.
     *
     * @param session the WebSocket session from which the message was received
     * @param message the text message received from the client
     * @throws Exception if an error occurs during message handling
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming messages (currently just printing them to the console)
        System.out.println("Received message from client: " + message.getPayload());
    }
}
