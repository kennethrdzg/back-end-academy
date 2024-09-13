import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {SHA256} from 'crypto-js';
import { UserToken } from '../entities/user-token';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SmalltalkService {
  private apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  registerUser(username: string, password: string): Observable<UserToken>{
    let password_hash: string = SHA256(password).toString();

    return this.http.post<UserToken>(this.apiUrl + "/users/register", {'username': username, 'passwordHash': password_hash});
  }

  logIn(username: string, password: string): Observable<UserToken>{
    let password_hash: string = SHA256(password).toString();
    return this.http.post<UserToken>(this.apiUrl + "/users/login", {'username': username, 'passwordHash': password_hash});
  }
}
