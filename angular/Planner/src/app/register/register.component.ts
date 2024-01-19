import { Component } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { RegisterRequest } from "../defs/authentication-defs";
import { Router } from "@angular/router";
import { NotificationService } from "../service/notification.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;
  submitted = false;

  constructor(
    private httpClient: HttpClient,
    private notificationService: NotificationService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.registerForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }


  onSubmit() {
    this.submitted = true;

    if (this.registerForm.invalid) {
      return;
    }

    const request: RegisterRequest = {
      email: this.registerForm.controls['email'].value,
      password: this.registerForm.controls['password'].value
    };

    this.httpClient.post('http://localhost:8080/api/auth/register', request).subscribe(
      (response) => {
        this.notificationService.showSnackBar('Register was completed successfully!', 'Go to login', () => this.router.navigate(['/login']))
      },
      (error) => {
        this.notificationService.showSnackBar('Email is already in use')
      }
    );
  }
}
