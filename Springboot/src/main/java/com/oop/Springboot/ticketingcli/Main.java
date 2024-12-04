package com.oop.Springboot.ticketingcli;

public class Main {
    public static void main(String[] args) {
        // Configuration values (can be replaced by user input or from a configuration file)
        int totalTickets = 20;           // Total number of tickets
        int ticketReleaseRate = 20;       // Number of tickets vendors will add per cycle
        int customerRetrievalRate = 20;   // Number of tickets customers will retrieve per cycle
        int maxCapacity = 20;              // Maximum capacity of the TicketPool

        // Initialize the ticket pool
        TicketPool ticketPool = new TicketPool(maxCapacity);

        // Create vendor threads to add tickets to the pool
        for (int i = 0; i < ticketReleaseRate; i++) {
            int vendorId = i + 1;
            // Pass the vendorId, ticketPool, totalTickets, and ticketReleaseRate to the Vendor constructor
            Vendor vendor = new Vendor(vendorId, ticketPool, totalTickets, ticketReleaseRate);
            Thread vendorThread = new Thread(vendor);
            vendorThread.start();
        }

        // Create customer threads to book tickets from the pool
        for (int i = 0; i < customerRetrievalRate; i++) {
            int customerId = i + 1;
            // Pass customerId, ticketPool, and booking delay to the Customer constructor
            Customer customer = new Customer(customerId, ticketPool, 1000); // 1000ms delay between bookings
            Thread customerThread = new Thread(customer);
            customerThread.start();
        }
    }
}
