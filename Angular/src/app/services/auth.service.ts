import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';  // <-- Import tap for handling side effects

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/auth';  // Backend API URL

  constructor(private http: HttpClient) {}

  // Register a new user
  register(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, user);
  }

  // Login user and store JWT token in localStorage
  login(credentials: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, credentials).pipe(
      tap((response: any) => {
        if (response && response.token) {
          // Check if we're in the browser before accessing localStorage
          if (typeof window !== 'undefined' && window.localStorage) {
            localStorage.setItem('auth_token', response.token);
            localStorage.setItem('user_role', response.role); 
          }
        }
      })
    );
  }

  // Get authorization headers with the token (for authenticated requests)
  private getAuthHeaders(): HttpHeaders {
    const token = typeof window !== 'undefined' && window.localStorage 
      ? localStorage.getItem('auth_token') 
      : null;
    return token ? new HttpHeaders().set('Authorization', `Bearer ${token}`) : new HttpHeaders();
  }

  // Check if the user is logged in (based on the presence of the JWT token)
  isLoggedIn(): boolean {
    // Check if we're in the browser before accessing localStorage
    return typeof window !== 'undefined' && window.localStorage 
      ? !!localStorage.getItem('auth_token') 
      : false;  // Return false if localStorage isn't available
  }

  // Logout user by removing the JWT token from localStorage
  logout(): void {
    if (typeof window !== 'undefined' && window.localStorage) {
      localStorage.removeItem('auth_token');
    }
  }

  // Example method to get user data using JWT token
  getUserData(): Observable<any> {
    return this.http.get(`${this.baseUrl}/user`, { headers: this.getAuthHeaders() });
  }
}
