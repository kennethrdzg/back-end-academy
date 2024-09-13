import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Post } from 'src/app/entities/post';
import { SessionService } from 'src/app/services/session.service';
import { SmalltalkApiService } from 'src/app/services/smalltalk-api.service';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.page.html',
  styleUrls: ['./feed.page.scss'],
})
export class FeedPage implements OnInit {

  username: string = '';
  token: string = '';
  posts: Post[] = [];
  
  postForm: FormGroup = new FormGroup({
    content: new FormControl('', [
      Validators.required, 
      Validators.minLength(1), 
      Validators.maxLength(128),
      Validators.pattern(/^\S(.*\S)?$/)
    ])
  });

  constructor(private router: Router, private sessionService: SessionService, private apiService: SmalltalkApiService) {
    const session = sessionService.getSession();
    if(!session.token){
      this.router.navigate(['home']);
    } else{
      this.token = session.token;
      this.username = session.username;
    }
  }

  ngOnInit() {
    this.getPosts();
    console.log(this.posts);
  }

  logOut(){
    this.sessionService.deleteSession();
    this.router.navigate(['home']);
  }

  getPosts(){
    this.posts = [];
    this.apiService.getPosts().subscribe(
      (res) => {
        res.forEach(
          (post) => {
            this.posts.push(post);
          }
        )
      }
    )
    console.log(this.posts);
  }

  uploadPost(){
    this.apiService.uploadPost(this.username, this.token, this.postContent?.getRawValue());
  }

  get postContent(){
    return this.postForm.get('content');
  }

}
