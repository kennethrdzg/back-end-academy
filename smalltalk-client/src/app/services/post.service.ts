import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../entities/post';
import { UserToken } from '../entities/user-token';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

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
