import { Component, OnInit, OnDestroy, AfterViewInit, ChangeDetectorRef, QueryList } from '@angular/core';
import { LogService } from '../../services/log.service';
import { HttpClient } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { WebSocketService } from '../../services/web-socket.service';
import { ViewChild, ViewChildren, ElementRef } from '@angular/core';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss'],
})
export class AdminDashboardComponent implements OnInit, OnDestroy, AfterViewInit {
  // Ticket-related properties
  currentTicketCount: number = 0;
  maxTicketCapacity: number = 1000;
  totalTickets: number = 500;
  ticketReleaseRate: number = 123;
  customerRetrievalRate: number = 43;

  logs: string[] = [];
  private logSubscription!: Subscription;
  private wsSubscription!: Subscription; // WebSocket subscription

  @ViewChild('logsContainer') private logsContainer!: ElementRef; // for scrolling
  @ViewChildren('logItem') private logItems!: QueryList<ElementRef>; // For observing changes to logs

  constructor(
    private http: HttpClient,
    private logService: LogService,
    private webSocketService: WebSocketService, // Inject WebSocketService
    private cdRef: ChangeDetectorRef // Inject ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    // Fetch initial ticketing config
    this.http.get<any>('http://localhost:8080/api/ticketing/config').subscribe(
      (response) => {
        this.totalTickets = response.totalTickets;
        this.ticketReleaseRate = response.ticketReleaseRate;
        this.customerRetrievalRate = response.customerRetrievalRate;
        this.maxTicketCapacity = response.maxTicketCapacity;
      },
      (error) => {
        console.error(error);
      }
    );

    // Subscribe to real-time logs from LogService
    this.logSubscription = this.logService.getLogs().subscribe(
      (logs: string[]) => {
        this.logs = logs; // Set the logs
        this.cdRef.detectChanges(); // Trigger change detection to update the view
        this.scrollToBottom(); // Ensure it scrolls after new logs
      },
      (error) => {
        console.error(error);
      }
    );

    // Connect to WebSocket service and subscribe for ticket count updates
    this.webSocketService.connect('ws://localhost:8080/ticket-progress');
    this.wsSubscription = this.webSocketService.getMessages().subscribe(
      (message: any) => {
        if (message && message.currentSize) {
          this.currentTicketCount = message.currentSize;
        }
        if (message && message.maxCapacity) {
          this.maxTicketCapacity = message.maxCapacity;
        }
      },
      (error) => {
        console.error('WebSocket error:', error);
      }
    );
  }

  ngAfterViewInit(): void {
    // Ensure the ViewChild is initialized before calling scrollToBottom
    if (this.logsContainer) {
      this.scrollToBottom();
    }
  }

  ngOnDestroy(): void {
    if (this.logSubscription) {
      this.logSubscription.unsubscribe();
    }
    if (this.wsSubscription) {
      this.wsSubscription.unsubscribe();
    }
    this.webSocketService.disconnect(); // Close WebSocket connection
  }

  // Compute the progress percentage for ticket pool
  get progress(): number {
    return (this.currentTicketCount / this.maxTicketCapacity) * 100;
  }

  // Submit form data to the backend to update ticketing configuration
  submitForm(): void {
    const formData = {
      totalTickets: this.totalTickets,
      ticketReleaseRate: this.ticketReleaseRate,
      customerRetrievalRate: this.customerRetrievalRate,
      maxTicketCapacity: this.maxTicketCapacity,
    };

    this.http.post('http://localhost:8080/api/ticketing/config', formData).subscribe(
      (response) => {
        console.log('Form Submitted:', response);
        this.scrollToBottom(); // Scroll to bottom after form submission
      },
      (error) => {
        console.error('Error updating config:', error);
      }
    );
  }

  // Stop the booking process and log the action
  stop(): void {
    this.http.post('http://localhost:8080/api/ticketing/stop', {}).subscribe(
      (response) => {
        console.log('Booking process stopped:', response);
      },
      (error) => {
        console.error('Error stopping the booking process:', error);
      }
    );
  }

  // Clear logs
  clearLogs(): void {
    this.logs = []; // Clear the logs array
  }

  // Scroll the logs container to the bottom
  scrollToBottom(): void {
    if (this.logsContainer) {
      const container = this.logsContainer.nativeElement;
      setTimeout(() => {
        container.scrollTop = container.scrollHeight;
      }, 100); // Delay to ensure the DOM is updated
    }
  }

  // Detect when logs change and scroll to the bottom
  private onLogsChanged(): void {
    this.scrollToBottom();
  }
}
