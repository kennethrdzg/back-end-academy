import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { createHash } from 'crypto';

@Injectable({
  providedIn: 'root'
})
export class SmalltalkService {
  private apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  registerUser(username: string, password: string){
    let password_hash: string = createHash('sha256').update(password).digest('hex');
    this.http.post(this.apiUrl, {'username': username, 'password': password_hash})
      .subscribe(
        (res) => {
          console.log(res);
        }
      );
  }

  logIn(username: string, password: string){
    let password_hash: string = createHash('sha256').update(password).digest('hex');
    this.http.post(this.apiUrl, {'username': username, 'password': password_hash})
      .subscribe(
        (res) => {
          console.log(res);
        }
      );
  }
}
