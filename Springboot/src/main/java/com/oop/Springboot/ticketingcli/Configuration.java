package com.oop.Springboot.ticketingcli;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    /**
     * Gets the total number of tickets to be generated.
     * @return totalTickets the number of tickets
     */
    public int getTotalTickets(){
        return totalTickets;
    }

    /**
     * Sets the total number of tickets to be generated.
     * @param totalTickets the number of tickets to be generated
     */
    public void setTotalTickets(int totalTickets){
        this.totalTickets = totalTickets;
    }

    /**
     * Gets the rate at which tickets are released by the vendor.
     * @return ticketReleaseRate the rate at which tickets are released
     */
    public int getTicketReleaseRate(){
        return ticketReleaseRate;
    }

    /**
     * Sets the rate at which tickets are released by the vendor.
     * @param ticketReleaseRate the rate at which tickets are released
     */
    public void setTicketReleaseRate(int ticketReleaseRate){
        this.ticketReleaseRate = ticketReleaseRate;
    }

    /**
     * Gets the rate at which customers retrieve tickets.
     * @return customerRetrievalRate the rate at which customers retrieve tickets
     */
    public int getCustomerRetrievalRate(){
        return customerRetrievalRate;
    }

    /**
     * Sets the rate at which customers retrieve tickets.
     * @param customerRetrievalRate the rate at which customers retrieve tickets
     */
    public void setCustomerRetrievalRate(int customerRetrievalRate){
        this.customerRetrievalRate = customerRetrievalRate;
    }

    /**
     * Gets the maximum capacity of the ticket pool.
     * @return maxTicketCapacity the maximum capacity
     */
    public int getMaxTicketCapacity(){
        return maxTicketCapacity;
    }

    /**
     * Sets the maximum capacity of the ticket pool.
     * @param maxTicketCapacity the maximum capacity of the ticket pool
     */
    public void setMaxTicketCapacity(int maxTicketCapacity){
        this.maxTicketCapacity = maxTicketCapacity;
    }

    /**
     * Saves the current configuration to a JSON file.
     * @param filePath the path where the configuration should be saved
     */
    public void saveToJson(String filePath) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(this, writer);  // Convert this configuration to JSON and write to file
            System.out.println("Configuration saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the configuration from a JSON file.
     * @param filePath the path from where the configuration should be loaded
     * @return a Configuration object populated with the data from the file, or null if loading fails
     */
    public static Configuration loadFromJson(String filePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Configuration.class);  // Convert JSON to Configuration object
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;  // Return null if loading fails
    }

    /**
     * Returns a string representation of the Configuration object.
     * @return a string representation of the Configuration
     */
    @Override
    public String toString() {
        return "Configuration{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                '}';
    }
}
