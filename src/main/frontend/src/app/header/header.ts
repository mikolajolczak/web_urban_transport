import {Component} from '@angular/core';
import {faChevronRight} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {RouterLink} from '@angular/router';

const tabs: Record<string, string> = {
  "Employees": "employee",
  "Vehicles": "vehicles",
  "Stops": "stops",
  "Offices": "offices",
  "About": "about"
};

@Component({
  selector: 'app-header',
  imports: [FontAwesomeModule, RouterLink],
  templateUrl: './header.html',
  styleUrl: './header.scss'
})
export class Header {
  faChevronRight = faChevronRight;
  tabs = tabs;
  logged = false;
  protected readonly Object = Object;

  toggleLogin() {
    this.logged = !this.logged;
  }
}
