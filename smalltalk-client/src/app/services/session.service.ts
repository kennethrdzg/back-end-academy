import { Injectable } from '@angular/core';
import { UserToken } from '../entities/user-token';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  private usernameCookie: string = 'username';
  private tokenCookie: string = 'token';

  constructor() { }

  // Save session in browser cookies
  saveSession(userToken: UserToken){
    document.cookie = this.usernameCookie + '=' + userToken.username;
    document.cookie = this.tokenCookie + '=' + userToken.token;
  }

  // Get session from cookies
  getSession(): UserToken {
    let userToken: UserToken = {username: '', token: ''};
    let cookies: string[] = document.cookie.split('; ');
    const usernameData = cookies.find( (cookie) => {
      return cookie.startsWith(this.usernameCookie + '=');
    })?.split('=')[1];
    const tokenData = cookies.find( (cookie) => {
      return cookie.startsWith(this.tokenCookie + '=');
    })?.split('=')[1];

    if(usernameData){
      userToken.username = usernameData;
    }
    if(tokenData){
      userToken.token = tokenData;
    }
    return userToken;
  }

  deleteSession(){
    document.cookie = this.usernameCookie + '=;';
    document.cookie = this.tokenCookie + '=;';
    // CookieJS.remove(this.usernameCookie);
    // CookieJS.remove(this.tokenCookie);
  }
}
