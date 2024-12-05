package com.oop.Springboot.ticketingcli;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerId;
    private final int bookingDelay;

    public Customer(int customerId, TicketPool ticketPool, int bookingDelay) {
        this.customerId = customerId;
        this.ticketPool = ticketPool;
        this.bookingDelay = bookingDelay;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Ticket bookedTicket = ticketPool.removeTicket(); // book a ticket
                if (bookedTicket != null) {
                    // log the ticket booking
                    LogManager.addLog("Customer " + customerId + " booked ticket no: " + bookedTicket.getTicketNumber());
                }

                // Wait for booking delay (simulate time between bookings)
                Thread.sleep(bookingDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
