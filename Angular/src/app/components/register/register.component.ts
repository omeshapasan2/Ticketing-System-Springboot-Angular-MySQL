import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';  // Adjust path if necessary
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  user: any = {
    username: '',
    password: '',
    role: 'Admin'  // Default role is 'Admin'
  };
  roles = ['Admin']; // Available roles

  constructor(private authService: AuthService, private router: Router) {}

  register() {
    // Assuming authService.register() expects the user object with username, password, and role
    this.authService.register(this.user).subscribe(
      () => {
        this.router.navigate(['/login']);
      },
      error => {
        console.error('Registration error: ', error);
      }
    );
  }
}
