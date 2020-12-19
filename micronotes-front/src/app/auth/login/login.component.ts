import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, FormBuilder, Validators} from '@angular/forms';
import {LoginPayload} from '../login-payload';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loginPayload: LoginPayload;
  submited = false;

  constructor(private authService: AuthService, private formBuilder: FormBuilder, private router: Router) {
    
    this.loginForm = this.formBuilder.group(
      {
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required]]
      }
    );

    this.loginPayload = {
      email: '',
      password: ''
    };
  }

  ngOnInit() {
  }

  onSubmit() {
    this.submited = true;
    if(this.loginForm.invalid){
      alert("invalid values");
      return;
    }
    this.loginPayload.email = this.loginForm.get('email').value;
    this.loginPayload.password = this.loginForm.get('password').value;

    this.login(this.loginPayload);

  }
  
  login(credetials: LoginPayload){
    this.authService.login(credetials)
      .toPromise()
      .then((data)=>{
        console.log('login success');
        this.router.navigateByUrl('/notes');
      })
      .catch((err)=>{
        console.log(err);
        console.log('Login failed');
        alert("Login failed!");
      });

  }

}