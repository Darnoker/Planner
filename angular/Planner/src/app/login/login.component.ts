import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {NotificationService} from "../service/notification.service";
import {Router} from "@angular/router";
import {LoginRequest} from "../defs/authentication-defs";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  submitted = false;

  constructor(
    private httpClient: HttpClient,
    private notificationService: NotificationService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    })
  }
  onSubmit(){
    this.submitted=true;

    if (this.loginForm.invalid){
      return;
    }

    const request: LoginRequest = {
      email: this.loginForm.controls['email'].value,
      password: this.loginForm.controls['password'].value
    };

    this.httpClient.post('http://localhost:8080/api/auth/authenticate', request).subscribe(
      (response: any) => {
        sessionStorage.setItem('token', response.token); //Zapisanie sesji zalogowania
        this.router.navigate(['/home']);
      },
      (error) => {
        console.error("Logowanie nie udane", error);
        this.notificationService.showSnackBar("Logowanie nie udane");
      }
    );
  }
}
