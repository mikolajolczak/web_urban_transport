import {inject, Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router} from '@angular/router';
import {UserService} from './user.service';

@Injectable({providedIn: 'root'})
export class RoleGuard implements CanActivate {

  private userService = inject(UserService);
  private router = inject(Router);

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole = route.data['role'];
    if (!this.userService.hasRole(expectedRole)) {
      this.router.navigate(['/error/403']);
      return false;
    }
    return true;
  }
}
