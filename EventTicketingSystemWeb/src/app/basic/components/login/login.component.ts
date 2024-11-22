import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { Router } from '@angular/router';
import { error } from 'console';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  validateForm!: FormGroup;

  constructor(private fb:FormBuilder,
    private authService: AuthService,
    private notification: NzNotificationService,
    private router: Router,
  ){

  }

  ngOnInit(){
    this.validateForm = this.fb.group({
      userName : [null, [Validators.required]],
      password : [null, [Validators.required]],      
    })
  }


  submitForm() {
    this.authService.login(
      this.validateForm.get('userName')!.value,
      this.validateForm.get('password')!.value
    ).subscribe(
      (res) => {
        console.log(res);
      },
      (error) => {
        this.notification.error(
          'ERROR',          
          'Bad credentials',
          { nzDuration: 5000 }  
        );
      }
    );
  }
  

}
