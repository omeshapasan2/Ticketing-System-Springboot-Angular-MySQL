// ticketing-status.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface TicketingStatus {
  totalTickets: number;
  maxTicketCapacity: number;
}

@Injectable({
  providedIn: 'root'
})
export class TicketingStatusService {
  private apiUrl = 'http://localhost:8080/api/ticketing-status'; // Adjust based on your API

  constructor(private http: HttpClient) {}

  getTicketingStatus(): Observable<TicketingStatus> {
    return this.http.get<TicketingStatus>(this.apiUrl);
  }
}
