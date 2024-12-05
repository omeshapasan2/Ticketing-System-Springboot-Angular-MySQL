package com.oop.Springboot.ticketingcli;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int vendorId;
    private final int totalTicketsToGenerate;
    private final int ticketReleaseRate;

    public Vendor(int vendorId, TicketPool ticketPool, int totalTicketsToGenerate, int ticketReleaseRate) {
        this.vendorId = vendorId;
        this.ticketPool = ticketPool;
        this.totalTicketsToGenerate = totalTicketsToGenerate;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public void run() {
        for (int i = 1; i <= totalTicketsToGenerate; i++) {
            Ticket ticket = new Ticket(i); // create a new Ticket object
            ticketPool.addTicket(ticket);  // add the ticket to the pool

            // log the ticket addition
            LogManager.addLog("Vendor " + vendorId + " added ticket no: " + ticket.getTicketNumber());

            // simulate the rate of ticket release
            try {
                Thread.sleep(1000 / ticketReleaseRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Vendor " + vendorId + " finished generating all tickets.");
    }
}
