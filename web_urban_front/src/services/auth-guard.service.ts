import {inject, Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {UserService} from './user.service';

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {

  private userService = inject(UserService);
  private router = inject(Router);


  canActivate(): boolean {
    if (!this.userService.isLoggedIn()) {
      this.router.navigate(['/login']);
      return false;
    }
    return true;
  }
}
