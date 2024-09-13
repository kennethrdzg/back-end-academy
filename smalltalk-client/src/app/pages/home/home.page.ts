import { Component, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive} from '@angular/router';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit{

  constructor(private router: Router, private sessionService: SessionService) {
  }

  ngOnInit(){
    const session = this.sessionService.getSession();
    if(session.token){
      this.router.navigate(['feed']);
    }
  }

}
