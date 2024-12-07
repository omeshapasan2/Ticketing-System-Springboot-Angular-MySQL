import { Component, OnInit, OnDestroy } from '@angular/core';
import { LogService } from '../../services/log.service';
import { HttpClient } from '@angular/common/http';
import { Subscription } from 'rxjs';

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

  constructor(private http: HttpClient, private logService: LogService) {}

  ngOnInit(): void {
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

    // subscribe to real time logs from websocket
    this.logSubscription = this.logService.getLogs().subscribe(
      (log: string) => {
        this.logs.push(log);  // put received log in the logs array
        console.log(log);  // log to the console
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

  // Stop the process and log the action (need fix)
  stop(): void {
    this.http.post('http://localhost:8080/api/ticketing/stop', {}).subscribe(
      (response) => {
        console.log('Booking process stopped:', response);
        this.logs.push('Booking process stopped successfully!'); // success message
      },
      (error) => {
        console.error('Error stopping the booking process:', error);
        this.logs.push('Failed to stop the booking process.'); // error message
      }
    );
  }

  // clear logs
  clearLogs(): void {
    this.logs = []; // Clear the logs array
  }

}
