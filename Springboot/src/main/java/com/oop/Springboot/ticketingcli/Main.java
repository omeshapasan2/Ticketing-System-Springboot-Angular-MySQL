package com.oop.Springboot.ticketingcli;

public class Main {

    // This is your existing method to run the ticketing process
    public static void runTicketingProcess(Configuration config) {
        int totalTickets = config.getTotalTickets();
        int ticketReleaseRate = config.getTicketReleaseRate();
        int customerRetrievalRate = config.getCustomerRetrievalRate();
        int maxCapacity = config.getMaxTicketCapacity();

        // Initialize the ticket pool
        TicketPool ticketPool = new TicketPool(maxCapacity);

        // Create vendor threads to add tickets to the pool
        for (int i = 0; i < ticketReleaseRate; i++) {
            int vendorId = i + 1;
            Vendor vendor = new Vendor(vendorId, ticketPool, totalTickets, ticketReleaseRate);
            Thread vendorThread = new Thread(vendor);
            vendorThread.start();
        }

        // Create customer threads to book tickets from the pool
        for (int i = 0; i < customerRetrievalRate; i++) {
            int customerId = i + 1;
            Customer customer = new Customer(customerId, ticketPool, 1000);
            Thread customerThread = new Thread(customer);
            customerThread.start();
        }
    }

    // Main method for running the application as a standalone Java app
    public static void main(String[] args) {
        // Create a sample configuration for testing
        Configuration config = new Configuration();
        config.setTotalTickets(100);
        config.setTicketReleaseRate(10);
        config.setCustomerRetrievalRate(5);
        config.setMaxTicketCapacity(50);

        // Run the ticketing process with the sample configuration
        runTicketingProcess(config);
    }
}
