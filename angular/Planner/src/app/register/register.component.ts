import { Component } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import * as http from "http";
import {RegisterRequest} from "../defs/authentication-defs";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  // Properties to bind to the form fields
  email: string = '';
  password: string = '';

  constructor(private httpClient: HttpClient) {

  }


  onSubmit() {
    const request: RegisterRequest = {
      email: this.email,
      password: this.password
    }
    console.log('halo')
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    this.httpClient.post('localhost:8080/api/auth/register', request).subscribe((result) => {
      console.log(result)
    } )

  }

}
