import { Component, OnInit, OnDestroy } from '@angular/core';
import { LogService } from '../../services/log.service';
import { HttpClient } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { TicketingStatusService } from '../../services/ticketing-status.service';
import { TicketingStatus } from '../../services/ticketing-status.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss'],
})
export class AdminDashboardComponent implements OnInit, OnDestroy {
  totalTickets: number = 500;
  ticketReleaseRate: number = 123;
  customerRetrievalRate: number = 43;
  maxTicketCapacity: number = 2;

  logs: string[] = []; 
  private logSubscription!: Subscription;

  constructor(
    private http: HttpClient, 
    private logService: LogService,
    private ticketingStatusService: TicketingStatusService // Combine both constructors here
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

    // Fetch ticketing status to get the current ticket count
    this.fetchTicketingStatus();

    // Subscribe to real-time logs from websocket
    this.logSubscription = this.logService.getLogs().subscribe(
      (log: string) => {
        this.logs.push(log);  // Put received log in the logs array
        console.log(log);  // Log to the console
      },
      (error) => {
        console.error(error);
      }
    );
  }

  ngOnDestroy(): void {
    // Clean up the subscription when the component is destroyed
    if (this.logSubscription) {
      this.logSubscription.unsubscribe();
    }
  }

  // Submit form data to the backend
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
      },
      (error) => {
        console.error('Error updating config:', error);
      }
    );
  }

  // Stop the process and log the action
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

  // Fetch the current ticketing status
  fetchTicketingStatus(): void {
    this.ticketingStatusService.getTicketingStatus().subscribe(
      (status: TicketingStatus) => {
        this.totalTickets = status.totalTickets;  // Update current ticket count
        this.maxTicketCapacity = status.maxTicketCapacity;
      },
      (error) => {
        console.error('Error fetching ticketing status:', error);
      }
    );
  }
}
