package com.oop.Springboot.controller;

import com.oop.Springboot.ticketingcli.Configuration;
import com.oop.Springboot.ticketingcli.Main;
import com.oop.Springboot.ticketingcli.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@RestController
@RequestMapping("/api/ticketing")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class TicketingController {

    private Configuration config;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    public TicketingController() {
        this.config = new Configuration();
    }

    @GetMapping("/config")
    public Configuration getConfig() {
        return this.config;
    }

    @PostMapping("/config")
    public ResponseEntity<Map<String, Object>> updateConfig(@RequestBody Configuration newConfig) {
        try {
            this.config.setTotalTickets(newConfig.getTotalTickets());
            this.config.setTicketReleaseRate(newConfig.getTicketReleaseRate());
            this.config.setCustomerRetrievalRate(newConfig.getCustomerRetrievalRate());
            this.config.setMaxTicketCapacity(newConfig.getMaxTicketCapacity());

            taskExecutor.execute(() -> Main.runTicketingProcess(config));

            LogManager.addLog("Configuration updated successfully!");

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Configuration updated successfully!");
            response.put("logs", LogManager.getLogs());  // Use the getLogs() method here

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error occurred while processing the configuration.");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/logs")
    public ResponseEntity<Map<String, Object>> getLogs() {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("logs", LogManager.getLogs());  // Use the getLogs() method here
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error occurred while retrieving logs.");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @DeleteMapping("/logs")
    public ResponseEntity<Map<String, Object>> clearLogs() {
        try {
            LogManager.clearLogs();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Logs cleared successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error occurred while clearing logs.");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    private boolean isProcessRunning = true;

    @PostMapping("/stop")
    public ResponseEntity<Void> stopTicketingProcess() {
        try {
            isProcessRunning = false; // Stop the process
            LogManager.addLog("Booking process stopped.");

            // Return a success response
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Return an error response if something goes wrong
            return ResponseEntity.status(500).build();
        }
    }
}
