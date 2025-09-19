import {Component} from '@angular/core';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {faChevronRight, faRightToBracket} from '@fortawesome/free-solid-svg-icons';
import {FormsModule} from '@angular/forms';
import {AuthService} from '../services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login-component',
  imports: [FontAwesomeModule, FormsModule],
  templateUrl: './login-component.html',
  styleUrl: './login-component.scss'
})
export class LoginComponent {
  faChevronRight = faChevronRight;
  faRightToBracket = faRightToBracket;
  login!: string;
  password!: string;

  constructor(private authService: AuthService, private router: Router) {

  }

  tryLogin() {
    this.authService.login(this.login, this.password).subscribe({
      next: () => this.router.navigate(['/']),
      error: () => console.error('Login failed'),
    });
  }
}
