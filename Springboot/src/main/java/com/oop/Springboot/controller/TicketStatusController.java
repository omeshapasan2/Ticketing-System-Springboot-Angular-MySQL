package com.oop.Springboot.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class TicketStatusController {
    public static int totalTickets;
    public static int maxTicketCapacity;

    @GetMapping("/ticketing-status")
    public TicketingStatus getTicketingStatus() {
        System.out.println("Ticketing status requested");
        return new TicketingStatus(totalTickets, maxTicketCapacity);
    }

    // update ticketing status
    public static void updateTicketingStatus(int newTotalTickets, int newMaxTicketCapacity) {
        totalTickets = newTotalTickets;
        maxTicketCapacity = newMaxTicketCapacity;
    }

    // DTO representing the ticketing status
    public static class TicketingStatus {
        private int totalTickets;
        private int maxTicketCapacity;

        public TicketingStatus(int totalTickets, int maxTicketCapacity) {
            this.totalTickets = totalTickets;
            this.maxTicketCapacity = maxTicketCapacity;
        }

        // Getters
        public int getTotalTickets() {
            return totalTickets;
        }

        public int getMaxTicketCapacity() {
            return maxTicketCapacity;
        }
    }
}
