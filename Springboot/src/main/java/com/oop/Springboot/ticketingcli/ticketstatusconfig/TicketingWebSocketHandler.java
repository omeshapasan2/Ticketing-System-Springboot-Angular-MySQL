package com.oop.Springboot.ticketingcli.ticketstatusconfig;

import com.oop.Springboot.controller.TicketStatusController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TicketingWebSocketHandler {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Send ticketing updates periodically to connected WebSocket clients
    @Scheduled(fixedRate = 2000) // Adjust rate as needed (e.g., every 2 seconds)
    public void sendTicketingUpdates() {
        TicketStatusController.TicketingStatus status = new TicketStatusController.TicketingStatus(
                TicketStatusController.totalTickets, TicketStatusController.maxTicketCapacity
        );
        messagingTemplate.convertAndSend("/topic/ticketing-status", status);
    }
}
