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

/**
 * TicketingController handles HTTP requests related to ticketing configuration and logs.
 * It allows retrieving, updating, and clearing ticketing configuration and logs.
 */
@RestController
@RequestMapping("/api/ticketing")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class TicketingController {

    private Configuration config;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * Constructs the TicketingController and initializes the default configuration.
     */
    public TicketingController() {
        this.config = new Configuration();
    }

    /**
     * Retrieves the current ticketing configuration.
     *
     * @return the current ticketing configuration
     */
    @GetMapping("/config")
    public Configuration getConfig() {
        return this.config;
    }

    /**
     * Updates the ticketing configuration and starts the ticketing process.
     * Logs the configuration update and returns the updated configuration along with logs.
     *
     * @param newConfig the new ticketing configuration to update
     * @return a response containing the success message and updated logs
     */
    @PostMapping("/config")
    public ResponseEntity<Map<String, Object>> updateConfig(@RequestBody Configuration newConfig) {
        try {
            this.config.setTotalTickets(newConfig.getTotalTickets());
            this.config.setTicketReleaseRate(newConfig.getTicketReleaseRate());
            this.config.setCustomerRetrievalRate(newConfig.getCustomerRetrievalRate());
            this.config.setMaxTicketCapacity(newConfig.getMaxTicketCapacity());

            // Start the ticketing process in a separate thread
            taskExecutor.execute(() -> Main.runTicketingProcess(config));

            // Add a log entry for the configuration update
            LogManager.addLog("Configuration updated successfully!");

            // Create response with success message and logs
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Configuration updated successfully!");
            response.put("logs", LogManager.getLogs());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle error and return response with error message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error occurred while processing the configuration.");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * Retrieves the current logs of the ticketing process.
     *
     * @return a response containing the current logs
     */
    @GetMapping("/logs")
    public ResponseEntity<Map<String, Object>> getLogs() {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("logs", LogManager.getLogs());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle error while retrieving logs
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error occurred while retrieving logs.");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * Clears the logs of the ticketing process.
     *
     * @return a response containing the success message
     */
    @DeleteMapping("/logs")
    public ResponseEntity<Map<String, Object>> clearLogs() {
        try {
            LogManager.clearLogs();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Logs cleared successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle error while clearing logs
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error occurred while clearing logs.");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    private boolean isProcessRunning = true;

    /**
     * Stops the ticketing process.
     *
     * @return a response with the success message or error message
     */
    @PostMapping("/stop")
    public ResponseEntity<String> stopTicketingProcess() {
        try {
            Main.stopTicketingProcess();
            return ResponseEntity.ok("Ticketing process stopped successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred while stopping the ticketing process: " + e.getMessage());
        }
    }
}
