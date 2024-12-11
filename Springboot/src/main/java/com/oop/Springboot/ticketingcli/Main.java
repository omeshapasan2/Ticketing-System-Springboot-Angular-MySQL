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

    /**
     * starts the ticketing process by initializing vendors and customers.
     * @param config configuration object with ticketing details
     */
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

    /**
     * stops the ticketing process by interrupting all vendor and customer threads.
     */
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

    /**
     * main method to run the ticketing app, either using previous config or new values.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Configuration config = null;
        File configFile = new File("config.json");

        // check if config.json exists
        if (configFile.exists()) {
            // ask user if they want to use the previous configuration or input new values
            System.out.print("Would you like to use the previous configuration (config.json)? (y/n): ");
            String userChoice = scanner.nextLine().trim().toLowerCase();

            if (userChoice.equals("y")) {
                config = Configuration.loadFromJson("config.json");
            }
        }

        // if config is still null, prompt the user for new input
        if (config == null) {
            System.out.println("No valid configuration found or user chose to input new values.");

            int totalTickets = -1;
            int ticketReleaseRate = -1;
            int customerRetrievalRate = -1;
            int maxTicketCapacity = -1;

            // get user input for configuration
            //----------------------------------

            // validate total number of tickets input
            while (totalTickets <= 0) {
                System.out.print("Enter total number of tickets: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Input an integer.");
                } else {
                    try {
                        totalTickets = Integer.parseInt(input);
                        if (totalTickets <= 0) {
                            System.out.println("Please enter a positive integer for total tickets.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter an integer.");
                    }
                }
            }

            // validate ticket release rate input
            while (ticketReleaseRate <= 0) {
                System.out.print("Enter ticket release rate (vendors): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Input an integer.");
                } else {
                    try {
                        ticketReleaseRate = Integer.parseInt(input);
                        if (ticketReleaseRate <= 0) {
                            System.out.println("Please enter a positive integer for ticket release rate.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter an integer.");
                    }
                }
            }

            // validate customer retrieval rate input
            while (customerRetrievalRate <= 0) {
                System.out.print("Enter customer retrieval rate (customers): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Input an integer.");
                } else {
                    try {
                        customerRetrievalRate = Integer.parseInt(input);
                        if (customerRetrievalRate <= 0) {
                            System.out.println("Please enter a positive integer for customer retrieval rate.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter an integer.");
                    }
                }
            }

            // validate maximum ticket capacity input
            while (maxTicketCapacity <= 0) {
                System.out.print("Enter maximum ticket capacity: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Input an integer.");
                } else {
                    try {
                        maxTicketCapacity = Integer.parseInt(input);
                        if (maxTicketCapacity <= 0) {
                            System.out.println("Please enter a positive integer for maximum ticket capacity.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter an integer.");
                    }
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

    /**
     * getter for isRunning flag.
     * @return the current state of ticketing process (running or not)
     */
    public static boolean isRunning() {
        return isRunning;
    }
}
