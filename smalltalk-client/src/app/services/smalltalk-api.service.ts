import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserToken } from '../entities/user-token';
import { SHA256 } from 'crypto-js';
import { Post } from '../entities/post';

@Injectable({
  providedIn: 'root'
})
export class SmalltalkApiService {
  
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

  getPosts(userToken: UserToken): Observable<Array<Post>>{
    return this.http.get<Array<Post>>(this.apiUrl + "/posts/"+userToken.userId+"/"+userToken.token);
  }

  uploadPost(userToken: UserToken, content: string): Observable<Post>{
    return this.http.post<Post>(this.apiUrl + "/posts/upload", {"username": userToken.username, "token": userToken.token, "content": content});
  }

  likeButtonPressed(userToken: UserToken, postId: number, liked: boolean): Observable<Post>{
    return this.http.post<Post>(this.apiUrl + "/posts/like", {"username": userToken.username, "token": userToken.token, "id": postId, "liked": liked});
  }
}
