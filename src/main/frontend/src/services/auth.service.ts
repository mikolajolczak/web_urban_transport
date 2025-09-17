import {Injectable} from '@angular/core';
import {Router} from '@angular/router';

export type UserRole = 'ADMIN' | 'USER';

export interface User {
  username: string;
  role: UserRole;
  token?: string;
}

@Injectable({providedIn: 'root'})
export class AuthService {
  private currentUser?: User;

  constructor(private router: Router) {
  }

  login(username: string, password: string): boolean {
    if (username === 'admin' && password === 'admin') {
      this.currentUser = {username, role: 'ADMIN', token: 'fake-jwt-admin'};
    } else if (username === 'user' && password === 'user') {
      this.currentUser = {username, role: 'USER', token: 'fake-jwt-user'};
    } else {
      return false;
    }
    if (typeof window !== 'undefined' && window.localStorage) {
      localStorage.setItem('user', JSON.stringify(this.currentUser));
      return true;
    }
    return false;
  }

  logout() {
    this.currentUser = undefined;
    if (typeof window !== 'undefined' && window.localStorage) {
      localStorage.removeItem('user');
    }
    this.router.navigate(['/']);
  }

  getUser(): User | undefined {
    if (!this.currentUser && typeof window !== 'undefined' && window.localStorage) {
      const user = localStorage.getItem('user');
      if (user) this.currentUser = JSON.parse(user);
    }
    return this.currentUser;
  }

  isLoggedIn(): boolean {
    return !!this.currentUser;
  }

  hasRole(role: UserRole): boolean {
    return this.getUser()?.role === role;
  }

  getRole(): UserRole | undefined {
    return this.getUser()?.role
  }
}
