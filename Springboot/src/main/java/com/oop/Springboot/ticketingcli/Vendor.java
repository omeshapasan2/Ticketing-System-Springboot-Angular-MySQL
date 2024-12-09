package com.oop.Springboot.ticketingcli;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int vendorId;
    private final int totalTicketsToGenerate;
    private final int ticketReleaseRate;

    /**
     * constructor to initialize the vendor with necessary details.
     * @param vendorId the unique id for the vendor
     * @param ticketPool the pool where tickets will be added
     * @param totalTicketsToGenerate the total number of tickets the vendor will generate
     * @param ticketReleaseRate the rate at which tickets are released (added to the pool)
     */
    public Vendor(int vendorId, TicketPool ticketPool, int totalTicketsToGenerate, int ticketReleaseRate) {
        this.vendorId = vendorId;
        this.ticketPool = ticketPool;
        this.totalTicketsToGenerate = totalTicketsToGenerate;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    /**
     * generates tickets and adds them to the ticket pool at a set release rate.
     * logs and prints details of each ticket added.
     */
    @Override
    public void run() {
        for (int i = 1; i <= totalTicketsToGenerate; i++) {
            Ticket ticket = new Ticket(i); // create a new ticket object
            ticketPool.addTicket(ticket);  // add the ticket to the pool

            // log ticket addition
            LogManager.addLog("Vendor " + vendorId + " added ticket no: " + ticket.getTicketNumber());
            System.out.println("Vendor " + vendorId + " added ticket no: " + ticket.getTicketNumber());

            // simulate the rate of ticket release
            try {
                Thread.sleep(1000 / ticketReleaseRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        LogManager.addLog("Vendor " + vendorId + " finished generating all tickets.");
        System.out.println("Vendor " + vendorId + " finished generating all tickets.");
    }
}
