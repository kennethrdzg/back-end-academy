import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, Validators, ReactiveFormsModule} from '@angular/forms';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { SmalltalkService } from '../../services/smalltalk.service';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, FormsModule, ReactiveFormsModule],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss'
})
export class AuthComponent {
  loading: boolean = false;
  formType: string = 'register';
  usernameError: string = '';
  passwordError: string = '';

  registerForm = new FormGroup({
    username: new FormControl('', [
      Validators.required, 
      Validators.minLength(4),
      Validators.maxLength(24),
      Validators.pattern("^[a-zA-Z]([a-zA-Z0-9])*$")
    ]), 
    password: new FormControl('', [
      Validators.required, 
      Validators.minLength(4), 
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

  constructor(private router: Router, private smalltalk: SmalltalkService){
    this.router = router;
  }

  handleFormTypeChange(formType: string){
    this.formType = formType;
  }

  handleFormSubmit(){
    if(this.formType === 'register'){
      this.smalltalk.registerUser(this.username?.getRawValue(), this.password?.getRawValue());
    } else if(this.formType === 'login'){
      this.smalltalk.logIn(this.username?.getRawValue(), this.password?.getRawValue());
    }
  }

  get username(){
    if(this.formType === 'register')
      return this.registerForm.get('username')
    else
      return this.loginForm.get('username')
  }

  get password(){
    if(this.formType === 'register')
      return this.registerForm.get('username')
    else
      return this.loginForm.get('password')
  }
}
