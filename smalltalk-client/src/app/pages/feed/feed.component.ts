import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [],
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.scss'
})
export class FeedComponent {
  username: string = '';
  constructor(private router: Router,private authservice: AuthenticationService){
    const session = authservice.getSession()
    if(!session.token){
      this.router.navigate(['home']);
    }
    else{
      this.username = session.username;
    }
  }

  logOut(){
    this.authservice.deleteSession();
    this.router.navigate(['home']);
  }
}
