package com.oop.Springboot.ticketingcli;

import java.util.List;
import java.util.Collections;
import java.util.LinkedList;

import com.oop.Springboot.ticketingcli.statuswebsocketconfig.TicketProgressHandler;
import org.springframework.web.socket.WebSocketSession;

public class TicketPool {
    private final List<Ticket> tickets = Collections.synchronizedList(new LinkedList<>());
    private final int maxCapacity;

    // List to hold WebSocket sessions
    private final List<WebSocketSession> sessions = Collections.synchronizedList(new LinkedList<>());

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    // Method to add a WebSocket session
    public synchronized void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    // Method to remove a WebSocket session
    public synchronized void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

    // Method to notify all WebSocket clients about the current pool size
    private void notifyAllClients() {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                // Retrieve the handler from session attributes
                TicketProgressHandler handler = (TicketProgressHandler) session.getAttributes().get("handler");
                if (handler != null) {
                    handler.sendTicketPoolSize(session, getCurrentSize(), maxCapacity);
                }
            }
        }
    }

    public synchronized void addTicket(Ticket ticket) {
        if (tickets.size() < maxCapacity) {
            tickets.add(ticket); // add Ticket object to the list
            LogManager.addLog("Vendor added ticket no: " + ticket.getTicketNumber());
            LogManager.addLog("Current ticket count: " + tickets.size());
            notifyAllClients();  // Notify all connected WebSocket clients
            notifyAll(); // For other threads waiting on tickets
        } else {
            LogManager.addLog("Ticket Pool is full. Vendor couldn't add ticket no: " + ticket.getTicketNumber());
        }
    }

    public synchronized Ticket removeTicket() {
        while (tickets.isEmpty()) {
            try {
                System.out.println("No tickets available. Waiting...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Ticket ticket = tickets.remove(0); // remove and return Ticket object
        LogManager.addLog("Customer booked ticket no: " + ticket.getTicketNumber());
        LogManager.addLog("Current ticket count: " + tickets.size());
        notifyAllClients();  // Notify all connected WebSocket clients
        return ticket;
    }

    public synchronized int getCurrentSize() {
        return tickets.size();
    }
}
