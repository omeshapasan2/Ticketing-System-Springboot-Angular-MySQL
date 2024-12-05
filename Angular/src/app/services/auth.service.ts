import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/auth';  // url of the backend api

  constructor(private http: HttpClient) {}

  // register a new user
  register(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, user);
  }

  // login user and save jwt token to localStorage
  login(credentials: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, credentials).pipe(
      tap((response: any) => {
        if (response && response.token) {
          // ensure localStorage is accessible in the browser
          if (typeof window !== 'undefined' && window.localStorage) {
            localStorage.setItem('auth_token', response.token);
            localStorage.setItem('user_role', response.role); 
          }
        }
      })
    );
  }

  // create authorization headers with jwt token for authenticated requests
  private getAuthHeaders(): HttpHeaders {
    const token = typeof window !== 'undefined' && window.localStorage 
      ? localStorage.getItem('auth_token') 
      : null;
    return token ? new HttpHeaders().set('Authorization', `Bearer ${token}`) : new HttpHeaders();
  }

  // check if the user is logged in based on the presence of a jwt token
  isLoggedIn(): boolean {
    // ensure localStorage is accessible in the browser
    return typeof window !== 'undefined' && window.localStorage 
      ? !!localStorage.getItem('auth_token') 
      : false;  // return false if localStorage is not available
  }

  // logout the user by removing the jwt token from localStorage
  logout(): void {
    if (typeof window !== 'undefined' && window.localStorage) {
      localStorage.removeItem('auth_token');
    }
  }

  // fetch user data using the jwt token
  getUserData(): Observable<any> {
    return this.http.get(`${this.baseUrl}/user`, { headers: this.getAuthHeaders() });
  }
}
