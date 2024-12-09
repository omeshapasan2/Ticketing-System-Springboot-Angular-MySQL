package com.oop.Springboot.ticketingcli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.File;
import java.util.Scanner;

import java.io.File;
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
        System.out.println("Starting ticketing process with " + totalTickets + " tickets.");

        isRunning = true;

        // create vendor threads to add tickets
        for (int i = 0; i < ticketReleaseRate; i++) {
            int vendorId = i + 1;
            Vendor vendor = new Vendor(vendorId, ticketPool, totalTickets, ticketReleaseRate);
            Thread vendorThread = new Thread(vendor);
            vendorThread.start();
            vendorThreads.add(vendorThread);
            LogManager.addLog("Vendor " + vendorId + " started.");
            System.out.println("Vendor " + vendorId + " started.");
        }

        // create customer threads to book tickets
        for (int i = 0; i < customerRetrievalRate; i++) {
            int customerId = i + 1;
            Customer customer = new Customer(customerId, ticketPool, 1000);
            Thread customerThread = new Thread(customer);
            customerThread.start();
            customerThreads.add(customerThread);
            LogManager.addLog("Customer " + customerId + " started.");
            System.out.println("Customer " + customerId + " started.");
        }

        LogManager.addLog("Ticketing process initialized with " + ticketReleaseRate + " vendors and " + customerRetrievalRate + " customers.");
        System.out.println("Ticketing process initialized with " + ticketReleaseRate + " vendors and " + customerRetrievalRate + " customers.");
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
        System.out.println("Ticketing process stopped.");
    }

    // main method to run the app
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Configuration config = null;
        File configFile = new File("config.json");

        // Check if config.json exists
        if (configFile.exists()) {
            // Ask user if they want to use the previous configuration or input new values
            System.out.print("Would you like to use the previous configuration (config.json)? (y/n): ");
            String userChoice = scanner.nextLine().trim().toLowerCase();

            if (userChoice.equals("y")) {
                config = Configuration.loadFromJson("config.json");
            }
        }

        // If config is still null, prompt the user for new input
        if (config == null) {
            System.out.println("No valid configuration found or user chose to input new values.");

            int totalTickets = -1;
            int ticketReleaseRate = -1;
            int customerRetrievalRate = -1;
            int maxTicketCapacity = -1;

            // get user input for configuration
            //----------------------------------

            // Validate total number of tickets input
            while (totalTickets <= 0) {
                System.out.print("Enter total number of tickets: ");
                if (scanner.hasNextInt()) {
                    totalTickets = scanner.nextInt();
                    if (totalTickets <= 0) {
                        System.out.println("Please enter a positive integer for total tickets.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter an integer.");
                    scanner.next(); // clear invalid input
                }
            }

            // Validate ticket release rate input
            while (ticketReleaseRate <= 0) {
                System.out.print("Enter ticket release rate (vendors): ");
                if (scanner.hasNextInt()) {
                    ticketReleaseRate = scanner.nextInt();
                    if (ticketReleaseRate <= 0) {
                        System.out.println("Please enter a positive integer for ticket release rate.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter an integer.");
                    scanner.next(); // clear invalid input
                }
            }

            // Validate customer retrieval rate input
            while (customerRetrievalRate <= 0) {
                System.out.print("Enter customer retrieval rate (customers): ");
                if (scanner.hasNextInt()) {
                    customerRetrievalRate = scanner.nextInt();
                    if (customerRetrievalRate <= 0) {
                        System.out.println("Please enter a positive integer for customer retrieval rate.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter an integer.");
                    scanner.next(); // clear invalid input
                }
            }

            // Validate maximum ticket capacity input
            while (maxTicketCapacity <= 0) {
                System.out.print("Enter maximum ticket capacity: ");
                if (scanner.hasNextInt()) {
                    maxTicketCapacity = scanner.nextInt();
                    if (maxTicketCapacity <= 0) {
                        System.out.println("Please enter a positive integer for maximum ticket capacity.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter an integer.");
                    scanner.next(); // clear invalid input
                }
            }

            // create configuration object and set values
            config = new Configuration();
            config.setTotalTickets(totalTickets);
            config.setTicketReleaseRate(ticketReleaseRate);
            config.setCustomerRetrievalRate(customerRetrievalRate);
            config.setMaxTicketCapacity(maxTicketCapacity);

            // save the configuration to a file
            config.saveToJson("config.json");
        }

        // run the ticketing process with the config
        runTicketingProcess(config);

        LogManager.addLog("Ticketing process complete.");
        System.out.println("Ticketing process complete.");

        scanner.close();
    }

    // getter for isRunning
    public static boolean isRunning() {
        return isRunning;
    }
}
