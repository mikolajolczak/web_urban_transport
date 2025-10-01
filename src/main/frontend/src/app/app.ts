import {Component, inject, OnInit} from '@angular/core';
import {Footer} from './footer/footer';
import {Header} from './header/header';
import {RouterOutlet} from '@angular/router';
import {UserService} from '../services/user.service';

@Component({
  selector: 'app-root',
  imports: [Footer, Header, RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {

  private userService = inject(UserService);

  ngOnInit() {
    this.userService.loadUser()
  }
}
