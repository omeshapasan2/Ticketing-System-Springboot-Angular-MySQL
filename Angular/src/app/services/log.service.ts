import { Injectable } from '@angular/core';
import { WebSocketSubject } from 'rxjs/webSocket';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LogService {
  private logSocket!: WebSocketSubject<any>;
  private logsSubject: BehaviorSubject<string[]> = new BehaviorSubject<string[]>([]);

  constructor() {
    this.connectToLogSocket();
  }

  private connectToLogSocket() {
    this.logSocket = new WebSocketSubject('ws://localhost:8080/logs');
    
    this.logSocket.subscribe({
      next: (message: any) => {
        try {
          if (typeof message === 'string') {
            this.pushLog(message);
          } else if (typeof message === 'object') {
            this.handleLog(message);
          }
        } catch (error) {
          console.error('Error processing log message:', error);
        }
      },
      error: (err) => {
        console.error('WebSocket error:', err);
        this.reconnect();
      },
      complete: () => {
        console.log('WebSocket connection closed');
        this.reconnect();
      },
    });
  }

  private handleLog(log: any) {
    if (log && log.hasOwnProperty('log')) {
      const logMessage = log.log;
      this.pushLog(logMessage);
    }
  }

  private pushLog(log: string) {
    console.log('Pushing log:', log);
    const currentLogs = this.logsSubject.value;
    this.logsSubject.next([...currentLogs, log]);
  }

  private reconnect() {
    setTimeout(() => this.connectToLogSocket(), 1000);
  }

  getLogs(): Observable<string[]> {
    return this.logsSubject.asObservable();
  }
}
