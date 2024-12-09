import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private webSocket: WebSocket | null = null;   // Keep track of WebSocket instance
  private messageSubject: Subject<any> = new Subject<any>();   // Observable for messages

  // Connect to WebSocket server
  connect(url: string): void {
    // If a WebSocket connection already exists, close it before creating a new one
    if (this.webSocket) {
      this.webSocket.close();
    }

    // Create a new WebSocket connection
    this.webSocket = new WebSocket(url);

    // Handle incoming WebSocket messages
    this.webSocket.onmessage = (event) => {
      const message = JSON.parse(event.data); // Parse incoming message and pass to subject
      this.messageSubject.next(message);
    };

    // Handle WebSocket connection open event
    this.webSocket.onopen = () => {
      console.log('WebSocket connection established');
    };

    // Handle WebSocket errors
    this.webSocket.onerror = (error) => {
      console.error('WebSocket error:', error);
    };

    // Handle WebSocket connection close event
    this.webSocket.onclose = () => {
      console.log('WebSocket connection closed');
    };
  }

  // Get observable for WebSocket messages
  getMessages() {
    return this.messageSubject.asObservable();
  }

  // Disconnect from WebSocket
  disconnect(): void {
    if (this.webSocket) {
      this.webSocket.close();
      this.webSocket = null;
    }
  }
}
