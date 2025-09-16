import {Component} from '@angular/core';
import {faChevronRight} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {RouterLink} from '@angular/router';
import {AuthService, UserRole} from '../../services/auth.service';

interface Tabs {
  name: string;
  url: string;
  roles: UserRole[];
}


const tabs: Tabs[] = [{
  name: "Employees",
  url: "employee",
  roles: ["ADMIN"]
}, {
  name: "Vehicles",
  url: "vehicles",
  roles: ["ADMIN"]
}, {
  name: "Stops",
  url: "stops",
  roles: ["ADMIN"]
}, {
  name: "Offices",
  url: "offices",
  roles: ["ADMIN"]
}, {
  name: "About",
  url: "about",
  roles: ["ADMIN", "USER"]
}];

@Component({
  selector: 'app-header',
  imports: [FontAwesomeModule, RouterLink],
  templateUrl: './header.html',
  styleUrl: './header.scss'
})
export class Header {
  faChevronRight = faChevronRight;
  tabs = tabs;

  constructor(private authService: AuthService) {
  }

  isLoggedIn() {
    return this.authService.isLoggedIn();
  }

  tryLogout() {
    this.authService.logout();
  }

  hasRole(roles: UserRole[]) {
    const currRole = this.authService.getRole();
    if (currRole) {
      return roles.includes(currRole);
    }
    return false
  }
}
