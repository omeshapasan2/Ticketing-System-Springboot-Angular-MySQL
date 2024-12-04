package com.oop.Springboot.ticketingcli;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int vendorId;                // Added vendorId
    private final int totalTicketsToGenerate;  // Total tickets to generate
    private final int ticketReleaseRate;       // Rate of ticket release (use for the delay)

    // Updated constructor to accept vendorId
    public Vendor(int vendorId, TicketPool ticketPool, int totalTicketsToGenerate, int ticketReleaseRate) {
        this.vendorId = vendorId;
        this.ticketPool = ticketPool;
        this.totalTicketsToGenerate = totalTicketsToGenerate;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public void run() {
        for (int i = 1; i <= totalTicketsToGenerate; i++) {
            Ticket ticket = new Ticket(i); // Create a new Ticket object
            ticketPool.addTicket(ticket);  // Add the ticket to the pool

            // Simulating the rate of ticket release (adjust sleep time as needed)
            try {
                Thread.sleep(1000 / ticketReleaseRate); // Release tickets based on release rate
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Gracefully handle thread interruption
                break;
            }
        }
        System.out.println("Vendor " + vendorId + " finished generating all tickets.");
    }
}
