import {Component} from '@angular/core';
import {Footer} from './footer/footer';
import {Header} from './header/header';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [Footer, Header, RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
}
