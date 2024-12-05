package com.oop.Springboot.ticketingcli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static boolean isRunning = false;
    private static List<Thread> vendorThreads = new ArrayList<>();
    private static List<Thread> customerThreads = new ArrayList<>();

    // start the ticketing process
    public static void runTicketingProcess(Configuration config) {
        LogManager.clearLogs();  // clear logs before starting

        int totalTickets = config.getTotalTickets();
        int ticketReleaseRate = config.getTicketReleaseRate();
        int customerRetrievalRate = config.getCustomerRetrievalRate();
        int maxCapacity = config.getMaxTicketCapacity();

        TicketPool ticketPool = new TicketPool(maxCapacity);

        LogManager.addLog("Starting ticketing process with " + totalTickets + " tickets.");

        isRunning = true;

        // create vendor threads to add tickets
        for (int i = 0; i < ticketReleaseRate; i++) {
            int vendorId = i + 1;
            Vendor vendor = new Vendor(vendorId, ticketPool, totalTickets, ticketReleaseRate);
            Thread vendorThread = new Thread(vendor);
            vendorThread.start();
            vendorThreads.add(vendorThread);
            LogManager.addLog("Vendor " + vendorId + " started.");
        }

        // create customer threads to book tickets
        for (int i = 0; i < customerRetrievalRate; i++) {
            int customerId = i + 1;
            Customer customer = new Customer(customerId, ticketPool, 1000);
            Thread customerThread = new Thread(customer);
            customerThread.start();
            customerThreads.add(customerThread);
            LogManager.addLog("Customer " + customerId + " started.");
        }

        LogManager.addLog("Ticketing process initialized with " + ticketReleaseRate + " vendors and " + customerRetrievalRate + " customers.");
    }

    // stop the ticketing process
    public static void stopTicketingProcess() {
        isRunning = false;

        // interrupt all vendor and customer threads
        for (Thread vendorThread : vendorThreads) {
            vendorThread.interrupt();
        }

        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
        }

        LogManager.addLog("Ticketing process stopped.");
    }

    // main method to run the app
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // get user input for configuration
        System.out.print("Enter total number of tickets: ");
        int totalTickets = scanner.nextInt();

        System.out.print("Enter ticket release rate (vendors): ");
        int ticketReleaseRate = scanner.nextInt();

        System.out.print("Enter customer retrieval rate (customers): ");
        int customerRetrievalRate = scanner.nextInt();

        System.out.print("Enter maximum ticket capacity: ");
        int maxTicketCapacity = scanner.nextInt();

        // create configuration object and set values
        Configuration config = new Configuration();
        config.setTotalTickets(totalTickets);
        config.setTicketReleaseRate(ticketReleaseRate);
        config.setCustomerRetrievalRate(customerRetrievalRate);
        config.setMaxTicketCapacity(maxTicketCapacity);

        // run the ticketing process with the config
        runTicketingProcess(config);

        LogManager.addLog("Ticketing process complete.");

        scanner.close();
    }

    // getter for isRunning
    public static boolean isRunning() {
        return isRunning;
    }
}
