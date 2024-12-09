import { Injectable } from '@angular/core';
import { Subject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private webSocket: WebSocket | null = null;   // Keep track of WebSocket instance
  private messageSubject: Subject<any> = new Subject<any>();   // Observable for messages

  // Connect to WebSocket server and return observable
  connect(url: string): Observable<any> {
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

    // Return observable for subscribers to listen to
    return this.messageSubject.asObservable();
  }

  // Disconnect from WebSocket
  disconnect(): void {
    if (this.webSocket) {
      this.webSocket.close();
      this.webSocket = null;
    }
  }

  send(url: string, message: any): void {
    if (this.webSocket) {
      this.webSocket.send(JSON.stringify({ url, message })); // Send message with the URL as part of the payload
    } else {
      console.error('WebSocket is not connected');
    }
  }
}
