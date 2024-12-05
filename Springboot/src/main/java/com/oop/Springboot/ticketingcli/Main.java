package com.oop.Springboot.ticketingcli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static boolean isRunning = false; // Flag to track the state of the process
    private static List<Thread> vendorThreads = new ArrayList<>();
    private static List<Thread> customerThreads = new ArrayList<>();

    // Method to run the ticketing process
    public static void runTicketingProcess(Configuration config) {
        // Clear logs at the start of each new process run
        LogManager.clearLogs();  // Clear logs before starting a new ticketing process

        int totalTickets = config.getTotalTickets();
        int ticketReleaseRate = config.getTicketReleaseRate();
        int customerRetrievalRate = config.getCustomerRetrievalRate();
        int maxCapacity = config.getMaxTicketCapacity();

        TicketPool ticketPool = new TicketPool(maxCapacity);

        // Log the start of the ticketing process
        LogManager.addLog("Starting ticketing process with " + totalTickets + " tickets.");

        // Set the process running flag to true
        isRunning = true;

        // Create vendor threads to add tickets to the pool
        for (int i = 0; i < ticketReleaseRate; i++) {
            int vendorId = i + 1;
            Vendor vendor = new Vendor(vendorId, ticketPool, totalTickets, ticketReleaseRate);
            Thread vendorThread = new Thread(vendor);
            vendorThread.start();
            vendorThreads.add(vendorThread);  // Keep track of vendor threads
            LogManager.addLog("Vendor " + vendorId + " started.");
        }

        // Create customer threads to book tickets from the pool
        for (int i = 0; i < customerRetrievalRate; i++) {
            int customerId = i + 1;
            Customer customer = new Customer(customerId, ticketPool, 1000);
            Thread customerThread = new Thread(customer);
            customerThread.start();
            customerThreads.add(customerThread);  // Keep track of customer threads
            LogManager.addLog("Customer " + customerId + " started.");
        }

        // Log that the process has been initialized
        LogManager.addLog("Ticketing process initialized with " +
                ticketReleaseRate + " vendors and " + customerRetrievalRate + " customers.");
    }


    // Method to stop the ticketing process
    public static void stopTicketingProcess() {
        // Set the running flag to false
        isRunning = false;

        // Interrupt all vendor and customer threads
        for (Thread vendorThread : vendorThreads) {
            vendorThread.interrupt();
        }

        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
        }

        // Optionally, log that the process was stopped
        LogManager.addLog("Ticketing process stopped.");
    }

    // Main method for running the application as a standalone Java app
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get user input for configuration
        System.out.print("Enter total number of tickets: ");
        int totalTickets = scanner.nextInt();

        System.out.print("Enter ticket release rate (vendors): ");
        int ticketReleaseRate = scanner.nextInt();

        System.out.print("Enter customer retrieval rate (customers): ");
        int customerRetrievalRate = scanner.nextInt();

        System.out.print("Enter maximum ticket capacity: ");
        int maxTicketCapacity = scanner.nextInt();

        // Create a configuration object and set the values from input
        Configuration config = new Configuration();
        config.setTotalTickets(totalTickets);
        config.setTicketReleaseRate(ticketReleaseRate);
        config.setCustomerRetrievalRate(customerRetrievalRate);
        config.setMaxTicketCapacity(maxTicketCapacity);

        // Run the ticketing process with the user-defined configuration
        runTicketingProcess(config);

        // Optionally, log that the process is complete
        LogManager.addLog("Ticketing process complete.");

        // Close the scanner
        scanner.close();
    }

    // Getter for isRunning (optional, can be used in the controller)
    public static boolean isRunning() {
        return isRunning;
    }
}
