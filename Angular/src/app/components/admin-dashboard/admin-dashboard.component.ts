import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss'],
})
export class AdminDashboardComponent implements OnInit {
  totalTickets: number = 500;
  ticketReleaseRate: number = 123;
  customerRetrievalRate: number = 43;
  maxTicketCapacity: number = 2;

  logs: string[] = []; // For storing logs

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    // Fetch configuration from backend
    this.http.get<any>('http://localhost:8080/api/ticketing/config').subscribe(
      (response) => {
        this.totalTickets = response.totalTickets || 500;
        this.ticketReleaseRate = response.ticketReleaseRate || 123;
        this.customerRetrievalRate = response.customerRetrievalRate || 43;
        this.maxTicketCapacity = response.maxTicketCapacity || 2;
      },
      (error) => {
        console.error('Error fetching configuration:', error);
      }
    );

    // Fetch logs from backend as JSON (response type is 'json' by default)
    this.http.get<any>('http://localhost:8080/api/ticketing/logs').subscribe(
      (response) => {
        console.log('Logs received:', response);  // Add this to check the response
        this.logs = response.logs || []; // Assign logs from the response
      },
      (error) => {
        console.error('Error receiving logs:', error);
      }
    );
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
        console.error('Error updating configuration:', error);
      }
    );
  }

  // Stop the process and log the action
  stop(): void {
    this.logs.push('Booking process stopped');
  }

  // Clear logs locally (Frontend)
  clearLogs(): void {
    this.logs = []; // Clear the logs array
  }

  // Optionally, clear logs on the backend
  clearLogsOnServer(): void {
    this.http.post('http://localhost:8080/api/ticketing/clear-logs', {}).subscribe(
      (response) => {
        console.log('Logs cleared on the server.');
        this.clearLogs(); // Clear the logs locally after clearing on the server
      },
      (error) => {
        console.error('Error clearing logs on the server:', error);
      }
    );
  }
}
