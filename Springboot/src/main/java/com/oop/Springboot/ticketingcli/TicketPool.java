package com.oop.Springboot.ticketingcli;

import java.util.List;
import java.util.Collections;
import java.util.LinkedList;

import com.oop.Springboot.ticketingcli.statuswebsocketconfig.TicketProgressHandler;
import org.springframework.web.socket.WebSocketSession;

public class TicketPool {
    private final List<Ticket> tickets = Collections.synchronizedList(new LinkedList<>());
    private final int maxCapacity;

    // list to hold websocket sessions
    private final List<WebSocketSession> sessions = Collections.synchronizedList(new LinkedList<>());

    /**
     * constructor to initialize the ticket pool with max capacity.
     * @param maxCapacity the maximum number of tickets the pool can hold
     */
    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    /**
     * adds a websocket session to the list of active sessions.
     * @param session the websocket session to add
     */
    public synchronized void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    /**
     * removes a websocket session from the list of active sessions.
     * @param session the websocket session to remove
     */
    public synchronized void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

    /**
     * notifies all connected websocket clients about the current ticket pool size.
     */
    private void notifyAllClients() {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                // retrieve the handler from session attributes
                TicketProgressHandler handler = (TicketProgressHandler) session.getAttributes().get("handler");
                if (handler != null) {
                    handler.sendTicketPoolSize(session, getCurrentSize(), maxCapacity);
                }
            }
        }
    }

    /**
     * adds a ticket to the pool if there's space available.
     * @param ticket the ticket to add to the pool
     */
    public synchronized void addTicket(Ticket ticket) {
        if (tickets.size() < maxCapacity) {
            tickets.add(ticket); // add ticket to the list
            LogManager.addLog("Vendor added ticket no: " + ticket.getTicketNumber());
            LogManager.addLog("Current ticket count: " + tickets.size());
            notifyAllClients();  // notify all connected websocket clients
            notifyAll(); // notify other threads waiting for tickets
        } else {
            LogManager.addLog("Ticket Pool is full. Vendor couldn't add ticket no: " + ticket.getTicketNumber());
        }
    }

    /**
     * removes a ticket from the pool. waits if no tickets are available.
     * @return the ticket removed from the pool
     */
    public synchronized Ticket removeTicket() {
        while (tickets.isEmpty()) {
            try {
                System.out.println("No tickets available. Waiting...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Ticket ticket = tickets.remove(0); // remove and return ticket from the pool
        LogManager.addLog("Customer booked ticket no: " + ticket.getTicketNumber());
        LogManager.addLog("Current ticket count: " + tickets.size());
        notifyAllClients();  // notify all connected websocket clients
        return ticket;
    }

    /**
     * retrieves the current size of the ticket pool.
     * @return the current number of tickets in the pool
     */
    public synchronized int getCurrentSize() {
        return tickets.size();
    }
}
