import { Injectable } from '@angular/core';
import { WebSocketSubject } from 'rxjs/webSocket';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LogService {
  private logSocket!: WebSocketSubject<any>;

  constructor() {
    this.connectToLogSocket();
  }

  private connectToLogSocket() {
    // Ensure correct WebSocket URL is provided (change 'localhost' to your server's address)
    this.logSocket = new WebSocketSubject('ws://localhost:8080/logs');

    // Subscribe to the WebSocket message stream
    this.logSocket.subscribe({
      next: (message: any) => {
        try {
          // Check if the message is already an object and stringify it if necessary
          if (typeof message === 'object') {
            console.log('New log received:', message);
            this.handleLog(message);  // Process the log object
          } else if (typeof message === 'string') {
            // If message is a string, directly log it
            console.log('New log received:', message);
            this.pushLog(message);  // Process the log string
          }
        } catch (error) {
          console.error('Error processing log message:', error);
        }
      },
      error: (err) => {
        console.error('WebSocket error:', err);
        this.reconnect();  // Attempt to reconnect if an error occurs
      },
      complete: () => {
        console.log('WebSocket connection closed');
        this.reconnect();  // Attempt to reconnect if the connection is closed
      },
    });
  }

  // Handle log message and convert it to a string if it's an object
  private handleLog(log: any) {
    if (log && log.hasOwnProperty('log')) {
      const logMessage = log.log;  // Extract the actual log message
      this.pushLog(logMessage);    // Push only the log message
    } else {
      // If the log is already a string (or in a different format), just push it as it is
      this.pushLog(log);
    }
  }

  private pushLog(log: string) {
    console.log('Pushing log:', log);  // Log the processed log to the console
    // Push the log string to the logs array or perform other operations as needed
  }

  // Attempt to reconnect if the WebSocket connection is lost
  private reconnect() {
    setTimeout(() => this.connectToLogSocket(), 1000); // Retry after 1 second
  }

  // Expose logs as an observable (optional if you're using Angular's Observable mechanism)
  getLogs(): Observable<string> {
    return new Observable((observer) => {
      this.logSocket.subscribe({
        next: (message: any) => {
          if (message && message.log) {
            observer.next(message.log);  // Emit only the log message
          }
        },
        error: (err) => observer.error(err),
        complete: () => observer.complete(),
      });
    });
  }
  
}
