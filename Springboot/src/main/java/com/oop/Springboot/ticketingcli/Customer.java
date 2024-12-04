package com.oop.Springboot.ticketingcli;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerId;
    private final int bookingDelay;    // Delay between ticket bookings

    public Customer(int customerId, TicketPool ticketPool, int bookingDelay) {
        this.customerId = customerId;
        this.ticketPool = ticketPool;
        this.bookingDelay = bookingDelay;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Ticket bookedTicket = ticketPool.removeTicket(); // Book a ticket
                System.out.println("Customer " + customerId + " booked ticket no: " + bookedTicket);

                // wait for 1s
                Thread.sleep(bookingDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
