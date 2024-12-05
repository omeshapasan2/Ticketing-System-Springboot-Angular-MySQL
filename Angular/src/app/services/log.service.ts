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
    // connect to the WebSocket server
    this.logSocket = new WebSocketSubject('ws://localhost:8080/logs');

    // subscribe to WebSocket messages
    this.logSocket.subscribe({
      next: (message: any) => {
        try {
          // process log message if it's an object or string
          if (typeof message === 'object') {
            console.log('New log received:', message);
            this.handleLog(message);  
          } else if (typeof message === 'string') {
            console.log('New log received:', message);
            this.pushLog(message); 
          }
        } catch (error) {
          console.error('Error processing log message:', error);
        }
      },
      error: (err) => {
        console.error('WebSocket error:', err);
        this.reconnect();  // try to reconnect on error
      },
      complete: () => {
        console.log('WebSocket connection closed');
        this.reconnect();  // reconnect if the connection closes
      },
    });
  }

  // process log message and extract log string
  private handleLog(log: any) {
    if (log && log.hasOwnProperty('log')) {
      const logMessage = log.log;  // get the log message
      this.pushLog(logMessage);    // push the log message
    } else {
      // if log is a string, directly push it
      this.pushLog(log);
    }
  }

  private pushLog(log: string) {
    console.log('Pushing log:', log);  // log the message to the console
  }

  // reconnect to the WebSocket after a 1sec
  private reconnect() {
    setTimeout(() => this.connectToLogSocket(), 1000);
  }

  // expose logs to other parts of the app
  getLogs(): Observable<string> {
    return new Observable((observer) => {
      this.logSocket.subscribe({
        next: (message: any) => {
          if (message && message.log) {
            observer.next(message.log);
          }
        },
        error: (err) => observer.error(err),
        complete: () => observer.complete(),
      });
    });
  }
}
