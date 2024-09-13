import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';
import { SmalltalkService } from '../../services/smalltalk.service';
import { Post } from '../../entities/post';

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [],
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.scss'
})
export class FeedComponent {
  
  username: string = '';

  posts: Post[] = [];
  
  constructor(private router: Router, private authservice: AuthenticationService, private smalltalkService: SmalltalkService){
    const session = authservice.getSession()
    if(!session.token){
      this.router.navigate(['home']);
    }
    else{
      this.username = session.username;
    }
    this.getPosts();
  }

  logOut(){
    this.authservice.deleteSession();
    this.router.navigate(['home']);
  }

  getPosts(){
    this.posts = [];
    this.smalltalkService.getPosts().subscribe(
      (p) =>{
        p.forEach(
          (post) => {
            this.posts.push(post);
          }
        )
      }
    )
    console.log(this.posts);
  }
}
