import { Component, OnInit } from '@angular/core';
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
  posts: Post[] = [];

  constructor(private router: Router, private sessionService: SessionService, private apiService: SmalltalkApiService) {
    const session = sessionService.getSession();
    if(!session.token){
      this.router.navigate(['home']);
    } else{
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
  }

}
