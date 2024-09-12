import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {SHA256} from 'crypto-js';

@Injectable({
  providedIn: 'root'
})
export class SmalltalkService {
  private apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  registerUser(username: string, password: string){
    let password_hash: string = SHA256(password).toString();
    
    this.http.post(this.apiUrl + "/users/register", {'username': username, 'passwordHash': password_hash})
      .subscribe(
        (res) => {
          console.log(res);
        }
      );
  }

  logIn(username: string, password: string){
    let password_hash: string = SHA256(password).toString();
    this.http.post(this.apiUrl + "/users/login", {'username': username, 'passwordHash': password_hash})
      .subscribe(
        (res) => {
          console.log(res);
        }
      );
  }
}
