import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-signup-vendor',
  templateUrl: './signup-vendor.component.html',
  styleUrl: './signup-vendor.component.scss'
})
export class SignupVendorComponent {
  validateForm!: FormGroup;

  constructor(private fb: FormBuilder
    ,private authService: AuthService
    ,private notification:NzNotificationService
    ,private router:Router){}
  
    ngOnInit(){
      this.validateForm = this.fb.group({
        email: [null,[Validators.email, Validators.required]],
        name: [null,[Validators.required]],
        lastname: [null,[Validators.required]],
        phone: [null,[Validators.required]],
        password: [null,[Validators.required]],
        checkPassword: [null,[Validators.required]],
      })
      
    }
  
    submitForm(){
      this.authService.registerCustomer(this.validateForm.value).subscribe(res =>{
        this.notification
          .success(
            'SUCCESS',
            'Signup Successful',
            { nzDuration: 5000 }
          );
          this.router.navigateByUrl('/login');
      },error =>{
        this.notification
        .error(
          'ERROR',
          `${error.error}`,
          {nzDuration: 5000}
        )
  
      });
    }
  
}
