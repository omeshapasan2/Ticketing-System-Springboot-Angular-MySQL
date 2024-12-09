package com.oop.Springboot.ticketingcli.statuswebsocketconfig;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class TicketProgressHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
        // For example, you can send an initial message or listen for incoming messages if needed
    }

    public void sendTicketPoolSize(WebSocketSession session, int currentSize, int maxCapacity) {
        try {
            String progressMessage = String.format("{\"currentSize\": %d, \"maxCapacity\": %d}", currentSize, maxCapacity);
            session.sendMessage(new TextMessage(progressMessage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
