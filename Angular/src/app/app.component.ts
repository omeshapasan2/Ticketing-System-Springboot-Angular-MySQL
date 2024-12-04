import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './services/auth.service';
import { isPlatformBrowser } from '@angular/common';  // Import this

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(
    private authService: AuthService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object  // Inject PLATFORM_ID to check if it's the browser
  ) {}

  // Check if the app is running in the browser
  private isBrowser(): boolean {
    return isPlatformBrowser(this.platformId);  // Return true if running in the browser
  }

  // Check if user is logged in by checking for auth token in localStorage
  isLoggedIn(): boolean {
    return this.isBrowser() && !!localStorage.getItem('auth_token');  // Check if auth token exists in localStorage
  }

  // Function to get the user role from localStorage
  getRole(): string | null {
    return this.isBrowser() ? localStorage.getItem('user_role') : null;  // Retrieve the user role from localStorage if in browser
  }

  // Logout functionality
  logout(): void {
    if (this.isBrowser()) {
      this.authService.logout();  // Assuming this method clears any relevant user session data
      localStorage.removeItem('auth_token');  // Remove auth token from localStorage
      localStorage.removeItem('user_role');  // Remove user role from localStorage
    }
    this.router.navigate(['/login']);  // Redirect user to login page after logout
  }
}
