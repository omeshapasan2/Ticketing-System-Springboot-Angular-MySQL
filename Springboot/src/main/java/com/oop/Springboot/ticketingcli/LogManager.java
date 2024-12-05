package com.oop.Springboot.ticketingcli;

import com.oop.Springboot.ticketingcli.websocketconfig.LogWebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogManager {
    private static List<String> logs = new ArrayList<>();
    private static final String filename = "DataFiles/Logs.txt"; // Path to the log file

    public static List<String> getLogs() {
        return logs;
    }

    public static void clearLogs() {
        logs.clear();
        clearLogFile();
    }

    public static synchronized void addLog(String logMessage) {
        logs.add(logMessage);
        writeLogToFile(logMessage);
    }

    // Helper method to write log to the file
    private static synchronized void writeLogToFile(String logMessage) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(logMessage + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + filename);
            e.printStackTrace();
        }
    }

    // Helper method to clear the log file
    private static void clearLogFile() {
        try (FileWriter writer = new FileWriter(filename)) {
            // Clear the contents of the log file
        } catch (IOException e) {
            System.err.println("Error clearing log file: " + filename);
            e.printStackTrace();
        }
    }

    private static WebSocketSession session;

    // Static method to send logs to WebSocket
    public static void sendLog(String log) {
        if (session != null && session.isOpen()) {
            LogWebSocketHandler.sendLog(session, log);
        }
        System.out.println(log); // Optionally log to the console
    }

    // Optional: add a method to set the WebSocket session (for instance, in the WebSocketConfig)
    public static void setSession(WebSocketSession session) {
        LogManager.session = session;
    }
}
