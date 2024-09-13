import { Injectable } from '@angular/core';
import * as CookieJS from 'js-cookie';
import { UserToken } from '../entities/user-token';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private usernameCookie: string = 'username';
  private tokenCookie: string = 'token';
  constructor() { }

  // Save Session in Browser Cookie
  saveSession(userToken: UserToken) {
    CookieJS.default.set(this.usernameCookie, userToken.username);
    CookieJS.default.set(this.tokenCookie, userToken.token);
  }

  // Get Session from Cookie
  getSession(): UserToken{
    let userToken: UserToken = {username: '', token: ''};
    const usernameData = CookieJS.default.get(this.usernameCookie);
    const tokenData = CookieJS.default.get(this.tokenCookie);
    if(usernameData){
      userToken.username = usernameData;
    }
    if(tokenData){
      userToken.token = tokenData;
    }

    return userToken;
  }

  deleteSession(){
    CookieJS.default.remove(this.usernameCookie);
    CookieJS.default.remove(this.tokenCookie);
  }

  // Delete Current Session
}
