import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Alert } from 'src/app/entities/alert';
import { Post } from 'src/app/entities/post';
import { UserToken } from 'src/app/entities/user-token';
import { NotificationService } from 'src/app/services/notification.service';
import { PostService } from 'src/app/services/post.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.page.html',
  styleUrls: ['./feed.page.scss'],
})
export class FeedPage implements OnInit {

  loading: boolean = false;
  posts: Post[] = [];
  alerts: Alert[] = [];

  private userToken: UserToken;
  
  postForm: FormGroup = new FormGroup({
    content: new FormControl('', [
      Validators.required, 
      Validators.minLength(1), 
      Validators.maxLength(128),
      Validators.pattern(/^\S(.*\S)?$/)
    ])
  });

  constructor(
    private router: Router,
    private sessionService: SessionService,
    private postService: PostService,
    private notificationService: NotificationService
  ) {

    const session = sessionService.getSession();
    if(!session.token){
      this.router.navigate(['home']);
    }
    this.userToken = {userId: session.userId, username: session.username, token: session.token};
  }

  ngOnInit() {
    this.getPosts();
    this.notificationService.subsciption()
      .watch(this.userToken.username)
      .subscribe((message) => {
        console.log(message.body);
        let a: Alert = JSON.parse(message.body);
        this.alerts.push(a);
        if(this.alerts.length > 10){
          this.alerts.pop();
        }
        this.posts = this.posts.map(
          (p) => {
            if(p.id != a.postId){
              return p;
            } else {
              p.likes = a.likes;
              console.log(a.message);
              return p;
            }
          }
        )
      });
  }

  logOut(){
    this.sessionService.deleteSession();
    this.router.navigate(['home']);
  }

  getPosts(){
    this.loading = true;
    this.posts = [];
    this.postService.getPosts(this.userToken).subscribe(
      (res) => {
        res.forEach(
          (post) => {
            this.posts.push(post);
          }
        )
      }
    )
    this.loading = false;
  }

  uploadPost(){
    this.loading = true;
    this.postService
      .uploadPost(
        this.userToken,
        this.postContent?.getRawValue()
      )
      .subscribe(
        (post) => {this.posts.splice(0, 0, post)}
      )
    this.loading = false;
  }

  likeButtonPressed(post: Post){
    post.liked = !post.liked;
    this.postService.likeButtonPressed(this.userToken, post.id, post.liked)
      .subscribe(
        (p) =>{
          let i = this.posts.indexOf(post);
          if(i > -1)
            this.posts[i].likes = p.likes;
        }
      )
  }

  deleteNotification(index: number){
    console.log("Index: " +index)
    this.alerts = this.alerts.filter(
      (n, i) =>{
        return i != index;
      }
    )
  }

  get postContent(){
    return this.postForm.get('content');
  }

  get username(){
    return this.userToken.username;
  }

}
