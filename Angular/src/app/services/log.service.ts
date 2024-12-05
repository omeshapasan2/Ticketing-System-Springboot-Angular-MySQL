import { Injectable } from '@angular/core';
import { WebSocketSubject } from 'rxjs/webSocket';
import { map } from 'rxjs/operators'; // Import map from rxjs/operators

@Injectable({
  providedIn: 'root',
})
export class LogService {
  private logSocket!: WebSocketSubject<string>; // Add definite assignment assertion

  constructor() {
    this.connectToLogSocket();
  }

  private connectToLogSocket() {
    // Connect to the WebSocket server
    this.logSocket = new WebSocketSubject('ws://localhost:8080/logs');
  }

  // Method to subscribe to logs as an observable
  getLogs() {
    return this.logSocket.asObservable().pipe(
      map((log: string) => {  // Specify the log type here
        return log;
      })
    );
  }

  // Optional: Method to send messages to the server (if needed)
  sendLog(log: string) {
    this.logSocket.next(log);
  }
}
