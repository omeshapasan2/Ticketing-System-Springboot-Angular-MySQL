import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  credentials: { username: string; password: string } = { username: '', password: '' };
  errorMessage: string = '';  // To display errors if login fails

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.authService.login(this.credentials).subscribe(
      (user: any) => {
        if (user && user.role) {
          // Check if we're in the browser before accessing localStorage
          if (typeof window !== 'undefined' && window.localStorage) {
            localStorage.setItem('auth_token', user.token);
          }

          // Redirect based on role
          if (user.role === 'Admin') {
            this.router.navigate(['/admin-dashboard']);
          } else{
            this.errorMessage = 'Login failed. Please check your credentials.';
          }
        }
      },
      error => {
        console.error('Login error: ', error);
        this.errorMessage = 'Login failed. Please check your credentials.';
      }
    );
  }
}
