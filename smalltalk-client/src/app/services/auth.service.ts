import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SHA256 } from 'crypto-js';
import { UserToken } from '../entities/user-token';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  private apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  registerUser(username: string, password: string): Observable<UserToken>{
    // let password_hash: string = SHA25
    let password_hash = SHA256(password).toString();

    return this.http.post<UserToken>(this.apiUrl + "/users/register", {'username': username, 'passwordHash': password_hash});
  }

  logIn(username: string, password: string): Observable<UserToken>{
    // let password_hash: string = SHA25
    let password_hash = SHA256(password).toString();

    return this.http.post<UserToken>(this.apiUrl + "/users/login", {'username': username, 'passwordHash': password_hash});
  }
}
