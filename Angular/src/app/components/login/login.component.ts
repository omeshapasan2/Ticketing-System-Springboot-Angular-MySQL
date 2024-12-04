import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { NzDrawerModule } from 'ng-zorro-antd/drawer';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  credentials: { username: string; password: string } = { username: '', password: '' };
  errorMessage: string = '';  // To display errors if login fails

  constructor(
    private authService: AuthService, 
    private router: Router, 
    private notification: NzNotificationService // Inject the notification service
  ) {}

  login() {
    this.authService.login(this.credentials).subscribe(
      (user: any) => {
        if (user && user.token) {
          // Check if we're in the browser before accessing localStorage
          if (typeof window !== 'undefined' && window.localStorage) {
            localStorage.setItem('auth_token', user.token);
          }

          // Redirect based on role
          if (user.role === 'Admin') {
            this.router.navigate(['/admin-dashboard']);
          } else {
            this.errorMessage = 'Login failed. You do not have the required role.';
          }
        } else {
          this.errorMessage = 'Login failed. Please check your credentials.';
        }
      },
      (error) => {
        console.error('Login error: ', error);
        this.errorMessage = 'Login failed. Please check your credentials.';
        
        // Show error popup with custom styling and position
        this.notification.error(
          'ERROR',
          'Bad credentials. Please check your username and password.',
          {
            nzDuration: 5000,
            nzStyle: {
              backgroundColor: '#ff4d4f', // Red background for error
              color: 'white',              // White text for contrast
              fontWeight: 'bold'           // Optional: make text bold for emphasis
            },
            nzPlacement: 'topRight' // Ensure this is set to topRight or other placements
          }
        );
      }
    );
  }
}
