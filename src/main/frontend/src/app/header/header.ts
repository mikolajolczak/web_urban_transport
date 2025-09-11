import {Component} from '@angular/core';
import {faChevronRight} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';

const tabs = ["Employees", "Vehicles", "Stops", "Offices", "About"]

@Component({
  selector: 'app-header',
  imports: [FontAwesomeModule],
  templateUrl: './header.html',
  styleUrl: './header.scss'
})
export class Header {
  faChevronRight = faChevronRight;
  tabs = tabs;
  logged = false;

  toggleLogin() {
    this.logged = !this.logged;
  }
}
