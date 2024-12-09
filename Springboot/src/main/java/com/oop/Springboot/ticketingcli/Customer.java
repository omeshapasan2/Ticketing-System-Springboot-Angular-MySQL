package com.oop.Springboot.ticketingcli;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerId;
    private final int bookingDelay;

    /**
     * Constructs a Customer instance for booking tickets from the ticket pool.
     * @param customerId the unique ID for the customer
     * @param ticketPool the shared ticket pool from which the customer books tickets
     * @param bookingDelay the time delay (in milliseconds) between each booking attempt
     */
    public Customer(int customerId, TicketPool ticketPool, int bookingDelay) {
        this.customerId = customerId;
        this.ticketPool = ticketPool;
        this.bookingDelay = bookingDelay;
    }

    /**
     * The run method simulates the customer's behavior of continuously booking tickets
     * from the ticket pool with a delay between each booking.
     */
    @Override
    public void run() {
        while (true) {
            try {
                // book a ticket from the pool
                Ticket bookedTicket = ticketPool.removeTicket();
                if (bookedTicket != null) {
                    // log the ticket booking
                    LogManager.addLog("Customer " + customerId + " booked ticket no: " + bookedTicket.getTicketNumber());
                    System.out.println("Customer " + customerId + " booked ticket no: " + bookedTicket.getTicketNumber());
                }

                // wait for the booking delay (simulate time between bookings)
                Thread.sleep(bookingDelay);
            } catch (InterruptedException e) {
                // handle thread interruption
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
