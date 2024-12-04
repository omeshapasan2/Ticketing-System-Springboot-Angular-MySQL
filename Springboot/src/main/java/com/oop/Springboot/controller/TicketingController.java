package com.oop.Springboot.controller;

import com.oop.Springboot.ticketingcli.Configuration;
import com.oop.Springboot.ticketingcli.Main;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticketing")
@CrossOrigin(origins = "http://localhost:4200" , allowedHeaders = "*")
public class TicketingController {

    private Configuration config;

    public TicketingController() {
        // Initialize the Configuration with empty values
        this.config = new Configuration();
    }

    // Endpoint to update configuration
    @PostMapping("/config")
    public String updateConfig(@RequestBody Configuration newConfig) {
        this.config.setTotalTickets(newConfig.getTotalTickets());
        this.config.setTicketReleaseRate(newConfig.getTicketReleaseRate());
        this.config.setCustomerRetrievalRate(newConfig.getCustomerRetrievalRate());
        this.config.setMaxTicketCapacity(newConfig.getMaxTicketCapacity());

        // Start the ticketing process with the updated config
        Main.runTicketingProcess(config);
        return "Configuration updated successfully!";
    }

    // Endpoint to get current configuration values
    @GetMapping("/config")
    public Configuration getConfig() {
        return this.config;
    }
}

