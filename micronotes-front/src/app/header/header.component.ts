import { Router } from '@angular/router';
import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  private fullName : string;

  constructor(private authService: AuthService, private router:Router) {
      this.fullName = this.authService.getFullName();
  }

  ngOnInit() {
  }

  logout() {
    this.authService.logout();
    this.router.navigateByUrl('/');
  }
}