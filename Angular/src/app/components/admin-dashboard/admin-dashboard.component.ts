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
    this.webSocketService.disconnect(); // close web socket
  }

  // progress bar
  get progress(): number {
    return (this.currentTicketCount / this.maxTicketCapacity) * 100;
  }

  // submit form data to the backend to update ticketing configuration
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
        this.scrollToBottom(); // scroll to bottom
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

  // clear logs
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

  // Save configuration to JSON file
  saveToJson(): void {
    const configData = {
      totalTickets: this.totalTickets,
      ticketReleaseRate: this.ticketReleaseRate,
      customerRetrievalRate: this.customerRetrievalRate,
      maxTicketCapacity: this.maxTicketCapacity,
    };

    const blob = new Blob([JSON.stringify(configData, null, 2)], { type: 'application/json' });
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = 'ticketing-config.json';
    link.click();
  }

  // Load configuration from a JSON file
  loadFromJson(event: any): void {
    const file = event.target.files[0];
    if (file) {
      console.log('Selected file:', file); // Log the file info
      if (file.type === 'application/json') {
        const reader = new FileReader();
        reader.onload = (e: any) => {
          try {
            const configData = JSON.parse(e.target.result);
            console.log('Loaded configuration:', configData); // Log loaded config
            this.totalTickets = configData.totalTickets || 0;
            this.ticketReleaseRate = configData.ticketReleaseRate || 0;
            this.customerRetrievalRate = configData.customerRetrievalRate || 0;
            this.maxTicketCapacity = configData.maxTicketCapacity || 0;
          } catch (err) {
            console.error('Error parsing JSON:', err); // Log JSON parsing errors
            alert('Invalid JSON file');
          }
        };
        reader.onerror = (err) => {
          console.error('Error reading file:', err); // Log file read errors
          alert('Error reading the file');
        };
        reader.readAsText(file);
      } else {
        alert('Please select a valid JSON file');
      }
    } else {
      alert('No file selected');
    }
  }
}
