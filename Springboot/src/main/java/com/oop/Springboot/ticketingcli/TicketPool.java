package com.oop.Springboot.ticketingcli;

import java.util.List;
import java.util.Collections;
import java.util.LinkedList;


public class TicketPool {
    private final List<Ticket> tickets = Collections.synchronizedList(new LinkedList<>());
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public synchronized void addTicket(Ticket ticket) {
        if (tickets.size() < maxCapacity) {
            tickets.add(ticket); // add Ticket object to the list
            LogManager.addLog("Vendor added ticket no: " + ticket.getTicketNumber());
            LogManager.addLog("Current ticket count: " + tickets.size());
            notifyAll();
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
        return ticket;
    }

    public synchronized int getCurrentSize() {
        return tickets.size();
    }
}
