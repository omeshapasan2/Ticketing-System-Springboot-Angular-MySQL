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

    public int getTotalTickets(){
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets){
        this.totalTickets=totalTickets;
    }

    public int getTicketReleaseRate(){
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate){
        this.ticketReleaseRate=ticketReleaseRate;
    }

    public int getCustomerRetrievalRate(){
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate){
        this.customerRetrievalRate=customerRetrievalRate;
    }

    public int getMaxTicketCapacity(){
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity){
        this.maxTicketCapacity=maxTicketCapacity;
    }


    // save configuration to json file
    public void saveToJson(String filePath) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(this, writer);  // convert to json and write it to the file
            System.out.println("Configuration saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load Configuration from JSON file
    public static Configuration loadFromJson(String filePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Configuration.class);  // Convert JSON from file to Configuration object
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;  // Return null if loading fails
    }

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
