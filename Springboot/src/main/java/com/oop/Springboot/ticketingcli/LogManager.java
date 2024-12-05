package com.oop.Springboot.ticketingcli;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogManager {
    private static final List<String> logs = Collections.synchronizedList(new ArrayList<>());
    private static final String filename = "DataFiles/Logs.txt"; // Path to the log file
    private static final List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<>());

    // retrieve all logs
    public static List<String> getLogs() {
        return new ArrayList<>(logs);
    }

    // clear logs in memory and in the log file
    public static void clearLogs() {
        synchronized (logs) {
            logs.clear();
        }
        clearLogFile();
    }

    // Add a log message > broadcast it to WebSocket clients > save it to the file
    public static void addLog(String logMessage) {
        synchronized (logs) {
            logs.add(logMessage);
        }

        // Broadcast to all WebSocket clients as JSON
        synchronized (sessions) {
            List<WebSocketSession> sessionCopy = new ArrayList<>(sessions);
            for (WebSocketSession session : sessionCopy) {
                if (session.isOpen()) {
                    try {
                        String jsonMessage = "{\"log\": \"" + logMessage + "\"}";
                        session.sendMessage(new TextMessage(jsonMessage));
                    } catch (IOException e) {
                        System.err.println("Error sending log to WebSocket client: " + e.getMessage());
                        removeSession(session);
                    }
                } else {
                    removeSession(session);
                }
            }
        }

        // Write to the log file
        writeLogToFile(logMessage);
    }


    // Write a log message to the log file
    private static void writeLogToFile(String logMessage) {
        synchronized (filename) {
            try (FileWriter writer = new FileWriter(filename, true)) {
                writer.write(logMessage + "\n");
            } catch (IOException e) {
                System.err.println("Error writing to log file: " + filename);
                e.printStackTrace();
            }
        }
    }

    // Clear the log file
    private static void clearLogFile() {
        synchronized (filename) {
            try (FileWriter writer = new FileWriter(filename)) {
                // empty line to clear the file
            } catch (IOException e) {
                System.err.println("Error clearing log file: " + filename);
                e.printStackTrace();
            }
        }
    }

    // Add a WebSocket session to the list
    public static void addSession(WebSocketSession session) {
        synchronized (sessions) {
            sessions.add(session);
        }
    }

    // Remove a WebSocket session from the list
    public static void removeSession(WebSocketSession session) {
        synchronized (sessions) {
            sessions.remove(session);
        }
    }
}
