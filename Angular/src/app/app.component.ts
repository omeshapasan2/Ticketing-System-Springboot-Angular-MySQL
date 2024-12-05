import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './services/auth.service';
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(
    private authService: AuthService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object  
  ) {}

  // Check if the app is running in the browser
  private isBrowser(): boolean {
    return isPlatformBrowser(this.platformId);  
  }

  // Check if user is logged in
  isLoggedIn(): boolean {
    return this.isBrowser() && !!localStorage.getItem('auth_token');  // Check if auth token is in localStorage
  }

  //]to get the user role from localStorage
  getRole(): string | null {
    return this.isBrowser() ? localStorage.getItem('user_role') : null;  // get the user role from local storage
  }

  // Logout function
  logout(): void {
    if (this.isBrowser()) {
      this.authService.logout(); 
      localStorage.removeItem('auth_token');  // Remove auth token from localStorage
      localStorage.removeItem('user_role');  // Remove user role from localStorage
    }
    this.router.navigate(['/login']);  // Redirect user to login page after logout
  }
}
