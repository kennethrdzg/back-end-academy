import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [ RouterLink, RouterLinkActive ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  constructor(private router: Router,private authservice: AuthenticationService){
    const session = authservice.getSession()
    if(session.token){
      this.router.navigate(['feed']);
    }
  }
}
