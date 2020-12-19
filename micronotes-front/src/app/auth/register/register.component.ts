import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RegisterPayload} from '../register-payload';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  registerPayload: RegisterPayload;
  submited = false;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router:Router) {
    
    this.registerForm = this.formBuilder.group(
      {
        firstName: ['', [Validators.required]],
        lastName: ['', [Validators.required]],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required]],
        confirmPassword: ['', [Validators.required]],
      }
    );

    this.registerPayload = {
      firstName: '',
      lastName: '',
      email: '',
      password: ''
    };
  }

  ngOnInit() {
  }
  
  onSubmit() {
    this.submited = true;
    if(this.registerForm.invalid){
      alert("invalid values");
      return;
    }
    if(this.registerForm.get('password').value != this.registerForm.get('confirmPassword').value){
      alert("Passwords don't match!");
      return;
    }

    this.registerPayload.firstName = this.registerForm.get('firstName').value;
    this.registerPayload.lastName = this.registerForm.get('lastName').value;
    this.registerPayload.email = this.registerForm.get('email').value;
    this.registerPayload.password = this.registerForm.get('password').value;

    this.authService.register(this.registerPayload)
      .toPromise()
      .then(() => {
        console.log('register succes');
        this.router.navigateByUrl('/register-success');
    }).catch((error) => {
      console.log(error);
      console.log('register failed');
    });
  }
}