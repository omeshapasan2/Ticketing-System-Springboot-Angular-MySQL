package com.oop.Springboot.ticketingcli.websocketconfig;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class LogWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, org.springframework.web.socket.TextMessage message) throws Exception {
        // Handle messages from clients (if any)
        super.handleTextMessage(session, message);
    }

    // Method to send log messages
    public static void sendLog(WebSocketSession session, String log) {
        try {
            if (session.isOpen()) {
                session.sendMessage(new org.springframework.web.socket.TextMessage(log));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

