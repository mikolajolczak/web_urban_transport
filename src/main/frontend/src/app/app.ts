import { Component, signal } from '@angular/core';
import {Footer} from './footer/footer';
import {Header} from './header/header';

@Component({
  selector: 'app-root',
  imports: [Footer, Header],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
}
