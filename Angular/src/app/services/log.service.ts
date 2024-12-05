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
          // Check if the message is already an object
          if (typeof message === 'object') {
            console.log('New log received:', message);
          } else if (typeof message === 'string') {
            // If message is a string, parse it as JSON
            const parsedMessage = JSON.parse(message);
            console.log('New log received:', parsedMessage);
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

  // Attempt to reconnect if the WebSocket connection is lost
  private reconnect() {
    setTimeout(() => this.connectToLogSocket(), 1000); // Retry after 1 second
  }

  // Expose logs as an observable (optional if you're using Angular's Observable mechanism)
  getLogs(): Observable<string> {
    return this.logSocket.asObservable();
  }
}
