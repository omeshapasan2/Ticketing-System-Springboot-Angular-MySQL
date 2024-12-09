package com.oop.Springboot.ticketingcli.statuswebsocketconfig;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

/**
 * WebSocket handler for managing ticket progress updates.
 * This handler is responsible for processing incoming messages and sending
 * updates about the ticket pool size to the WebSocket session.
 */
public class TicketProgressHandler extends TextWebSocketHandler {

    /**
     * Handles incoming text messages sent from the client over the WebSocket connection.
     * This implementation does not process the incoming messages, but can be extended to do so.
     *
     * @param session the WebSocket session for the connection
     * @param message the incoming text message
     * @throws InterruptedException if the thread is interrupted while waiting
     * @throws IOException if there is an issue sending or receiving the message
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
        // For example, you can send an initial message or listen for incoming messages if needed
    }

    /**
     * Sends a message to the client with the current size and maximum capacity of the ticket pool.
     * The message is sent as a JSON string.
     *
     * @param session the WebSocket session to which the message will be sent
     * @param currentSize the current number of tickets in the pool
     * @param maxCapacity the maximum capacity of the ticket pool
     */
    public void sendTicketPoolSize(WebSocketSession session, int currentSize, int maxCapacity) {
        try {
            String progressMessage = String.format("{\"currentSize\": %d, \"maxCapacity\": %d}", currentSize, maxCapacity);
            session.sendMessage(new TextMessage(progressMessage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
