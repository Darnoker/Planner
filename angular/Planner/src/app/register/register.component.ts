import { Component } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { RegisterRequest } from "../defs/authentication-defs";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  email: string = '';
  password: string = '';

  constructor(private httpClient: HttpClient) {
  }

  onSubmit() {
    const request: RegisterRequest = {
      email: this.email,
      password: this.password
    }
    this.httpClient.post('http://localhost:8080/api/auth/register', request).subscribe((response) => {
      console.log(response)
      // przejscie do widoku logowania
    })
  }
}
