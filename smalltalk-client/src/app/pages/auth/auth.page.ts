import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { SmalltalkApiService } from 'src/app/services/smalltalk-api.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.page.html',
  styleUrls: ['./auth.page.scss'],
})
export class AuthPage implements OnInit {

  loading: boolean = false;
  formType: string = 'register';
  usernameError: string = '';
  passwordError: string = '';
  passwordVisibility = 'password';
  

  registerForm = new FormGroup({
    username: new FormControl('', [
      Validators.required, 
      Validators.minLength(4),
      Validators.maxLength(24),
      Validators.pattern("^[a-zA-Z]([a-zA-Z0-9])*$")
    ]), 
    password: new FormControl('', [
      Validators.required, 
      Validators.minLength(8), 
      Validators.pattern("^([a-zA-Z0-9])+$")
    ])
  });

  loginForm = new FormGroup({
    username: new FormControl('', [
      Validators.required
    ]), 
    password: new FormControl('', [
      Validators.required
    ])
  })

  constructor(private router: Router, private apiService: SmalltalkApiService, private sessionService: SessionService) {
    if(this.router.url !== '/auth'){
      this.formType = this.router.url.substring(1);
    }
    // const session = authService.getSession();
    // if(session.token){
    //   this.router.navigate(['home']);
    // }
  }

  ngOnInit() {
    console.log("AuthComponent is ready");
  }

  handleFormTypeChange(formType: string){
    this.formType = formType;
    this.passwordVisibility = 'password';
  }

  handleFormSubmit(){
    this.loading = true;
    if(this.formType === 'register'){
      this.apiService.registerUser(this.username?.getRawValue(), this.password?.getRawValue())
        .subscribe(
          (userToken) => {
            this.sessionService.saveSession(userToken);
            this.router.navigate(['feed']);
          }
        )
    } else if(this.formType === 'login'){
      this.apiService.logIn(this.username?.getRawValue(), this.password?.getRawValue())
        .subscribe(
          (userToken) => {
            this.sessionService.saveSession(userToken);
            this.router.navigate(['feed']);
          }
        )
    }
    // if(this.formType === 'register'){
    //   this.smalltalk.registerUser(this.username?.getRawValue(), this.password?.getRawValue())
    //     .subscribe(
    //       (userToken) => {
    //         this.authService.saveSession(userToken);
    //         this.router.navigate(['feed'])
    //   });
    // } else if(this.formType === 'login'){
    //   this.smalltalk.logIn(this.username?.getRawValue(), this.password?.getRawValue())
    //     .subscribe(
    //       (userToken) => {
    //         this.authService.saveSession(userToken);
    //         this.router.navigate(['feed'])
    //   });
    // }
    this.loading = false;
  }

  updatePasswordVisibility(){
    if(this.passwordVisibility === 'password')
        this.passwordVisibility = 'text';
    else
      this.passwordVisibility = 'password';
  }

  get username(){
    if(this.formType === 'register')
      return this.registerForm.get('username')
    else
      return this.loginForm.get('username')
  }

  get password(){
    if(this.formType === 'register')
      return this.registerForm.get('password')
    else
      return this.loginForm.get('password')
  }

}
