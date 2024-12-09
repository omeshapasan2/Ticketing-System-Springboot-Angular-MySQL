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
    private static final String filename = "DataFiles/Logs.txt"; // path to the log file
    private static final List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<>());

    /**
     * retrieves all logs stored in memory.
     * @return a list of log messages
     */
    public static List<String> getLogs() {
        return new ArrayList<>(logs);
    }

    /**
     * clears all logs in memory and in the log file.
     */
    public static void clearLogs() {
        synchronized (logs) {
            logs.clear();
        }
        clearLogFile();
    }

    /**
     * adds a log message, broadcasts it to all WebSocket clients,
     * and writes it to the log file.
     * @param logMessage the log message to be added
     */
    public static void addLog(String logMessage) {
        synchronized (logs) {
            logs.add(logMessage);
        }

        // broadcast log message to all open WebSocket clients
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

        // write log to the log file
        writeLogToFile(logMessage);
    }

    /**
     * writes a log message to the log file.
     * @param logMessage the log message to be written
     */
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

    /**
     * clears the log file by overwriting it with an empty content.
     */
    private static void clearLogFile() {
        synchronized (filename) {
            try (FileWriter writer = new FileWriter(filename)) {
                // clear the file by writing nothing
            } catch (IOException e) {
                System.err.println("Error clearing log file: " + filename);
                e.printStackTrace();
            }
        }
    }

    /**
     * adds a WebSocket session to the list of sessions.
     * @param session the WebSocket session to be added
     */
    public static void addSession(WebSocketSession session) {
        synchronized (sessions) {
            sessions.add(session);
        }
    }

    /**
     * removes a WebSocket session from the list of sessions.
     * @param session the WebSocket session to be removed
     */
    public static void removeSession(WebSocketSession session) {
        synchronized (sessions) {
            sessions.remove(session);
        }
    }
}
