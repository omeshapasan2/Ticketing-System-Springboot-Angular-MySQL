package com.oop.Springboot.ticketingcli;

public class Main {

    public static void runTicketingProcess(Configuration config) {
        int totalTickets = config.getTotalTickets();
        int ticketReleaseRate = config.getTicketReleaseRate();
        int customerRetrievalRate = config.getCustomerRetrievalRate();
        int maxCapacity = config.getMaxTicketCapacity();

        TicketPool ticketPool = new TicketPool(maxCapacity);

        // Log the start of the ticketing process
        LogManager.addLog("Starting ticketing process with " + totalTickets + " tickets.");

        // Create vendor threads to add tickets to the pool
        for (int i = 0; i < ticketReleaseRate; i++) {
            int vendorId = i + 1;
            Vendor vendor = new Vendor(vendorId, ticketPool, totalTickets, ticketReleaseRate);
            Thread vendorThread = new Thread(vendor);
            vendorThread.start();
            LogManager.addLog("Vendor " + vendorId + " started.");
        }

        // Create customer threads to book tickets from the pool
        for (int i = 0; i < customerRetrievalRate; i++) {
            int customerId = i + 1;
            Customer customer = new Customer(customerId, ticketPool, 1000);
            Thread customerThread = new Thread(customer);
            customerThread.start();
            LogManager.addLog("Customer " + customerId + " started.");
        }

        // Log that the process has been initialized
        LogManager.addLog("Ticketing process initialized with " +
                ticketReleaseRate + " vendors and " + customerRetrievalRate + " customers.");
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

        // Optionally, log that the process is complete
        LogManager.addLog("Ticketing process complete.");
    }
}