import {Component, inject} from '@angular/core';
import {faChevronRight} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {RouterLink} from '@angular/router';
import {AuthService} from '../../services/auth.service';
import {UserRole, UserService} from '../../services/user.service';
import {Observable} from 'rxjs';
import {AsyncPipe} from '@angular/common';

interface Tabs {
  name: string;
  url: string;
  roles: UserRole[];
}

const tabs: Tabs[] = [{name: "Employees", url: "employee", roles: ["ADMIN"]}, {
  name: "Vehicles",
  url: "vehicles",
  roles: ["ADMIN"]
}, {name: "Stops", url: "stops", roles: ["ADMIN"]}, {name: "Offices", url: "offices", roles: ["ADMIN"]}, {
  name: "About",
  url: "about",
  roles: ["ADMIN", "USER"]
}];

@Component({
  selector: 'app-header',
  imports: [FontAwesomeModule, RouterLink, AsyncPipe],
  templateUrl: './header.html',
  styleUrl: './header.scss'
})
export class Header {
  faChevronRight = faChevronRight;
  tabs = tabs;
  isLoggedIn$: Observable<boolean>;
  role$: Observable<UserRole | null>;

  private userService = inject(UserService);
  private authService = inject(AuthService);


  constructor() {
    this.isLoggedIn$ = this.userService.isLoggedIn$;
    this.role$ = this.userService.role$;
  }


  tryLogout() {
    this.authService.logout().subscribe();
  }


  hasRole(roles: UserRole[], role: UserRole | null) {
    return role ? roles.includes(role) : false;
  }
}
