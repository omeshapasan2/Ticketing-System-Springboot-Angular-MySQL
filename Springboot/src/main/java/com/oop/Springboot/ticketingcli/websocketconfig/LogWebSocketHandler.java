package com.oop.Springboot.ticketingcli.websocketconfig;

import com.oop.Springboot.ticketingcli.LogManager;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class LogWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // remove session from log manager when disconnected
        LogManager.removeSession(session);
        System.out.println("WebSocket session closed: " + session.getId());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // add session to log manager to send messages
        LogManager.addSession(session);
        System.out.println("WebSocket session established: " + session.getId());

        // send initial message
        String jsonMessage = "{\"log\": \"Connected to the log stream.\"}";
        session.sendMessage(new TextMessage(jsonMessage));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // handle incoming messages
        System.out.println("Received message from client: " + message.getPayload());
    }
}
