import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {catchError, map} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {environment} from '../environments/environment';

export type UserRole = 'ADMIN' | 'USER';

export interface User {
  username: string;
  employee_id?: number | null;
  role: UserRole;
  token?: string;
}

export interface LoginResponse {
  role: UserRole;
  employeeId: number | null;
  token:string;
  username: string;
}

@Injectable({providedIn: 'root'})
export class AuthService {
  protected apiUrl = environment.apiUrl;
  private currentUser?: User;


  constructor(private router: Router, private http: HttpClient) {
  }

  login(username: string, password: string): Observable<boolean> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/auth/login`, {username, password}).pipe(
      map(response => {
        const user: User = {
          employee_id: response.employeeId, role: response.role, username: username, token: response.token
        }
        if (typeof window !== 'undefined' && window.localStorage) {
          localStorage.setItem('user', JSON.stringify(user));
        }
        return true;
      }),
      catchError(err => {
        console.error(err);
        return of(false);
      })
    );
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
