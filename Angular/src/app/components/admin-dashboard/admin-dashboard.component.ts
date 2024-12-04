import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss']
})
export class AdminDashboardComponent implements OnInit {
  totalTickets: number = 500;
  ticketReleaseRate: number = 123;
  customerRetrievalRate: number = 43;
  maxTicketCapacity: number = 2;

  // For storing logs or any responses
  logs: string[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    // Fetch current configuration from the backend on initialization
    this.http.get<any>('http://localhost:8080/api/ticketing/config')
      .subscribe(response => {
        this.totalTickets = response.totalTickets || 500;
        this.ticketReleaseRate = response.ticketReleaseRate || 123;
        this.customerRetrievalRate = response.customerRetrievalRate || 43;
        this.maxTicketCapacity = response.maxTicketCapacity || 2;
      });
  }

  // Submit the form data to the backend
  submitForm(): void {
    const formData = {
      totalTickets: this.totalTickets,
      ticketReleaseRate: this.ticketReleaseRate,
      customerRetrievalRate: this.customerRetrievalRate,
      maxTicketCapacity: this.maxTicketCapacity
    };

    this.http.post('http://localhost:8080/api/ticketing/config', formData)
      .subscribe(response => {
        this.logs.push('Configuration updated successfully!');
        console.log('Form Submitted:', response);
      }, error => {
        this.logs.push('Error updating configuration');
        console.error('Error:', error);
      });
  }

  // Stop the ticketing process (Optional, if relevant to your API)
  stop(): void {
    console.log('Booking process stopped');
    this.logs.push('Booking process stopped');
  }
}
