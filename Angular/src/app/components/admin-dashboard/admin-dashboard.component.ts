// src/app/components/admin-dashboard/admin-dashboard.component.ts

import { Component, OnInit, OnDestroy } from '@angular/core';
import { LogService } from '../../services/log.service';  // Correct path to LogService
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

  logs: string[] = []; // For storing logs
  private logSubscription!: Subscription;  // For unsubscribing from the log stream

  constructor(private http: HttpClient, private logService: LogService) {}

  ngOnInit(): void {
    // Fetch initial configuration
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

    // Subscribe to real-time logs from WebSocket
    this.logSubscription = this.logService.getLogs().subscribe(
      (log: string) => {
        this.logs.push(log);  // Add the received log to the logs array
        console.log('New log received:', log);  // Optionally log to the console
      },
      (error) => {
        console.error('Error receiving logs:', error);
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
