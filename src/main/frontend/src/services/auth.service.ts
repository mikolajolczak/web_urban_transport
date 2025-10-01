import {inject, Injectable} from '@angular/core';
import {tap} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../environments/environment';
import {UserService} from './user.service';
import {Router} from '@angular/router';

@Injectable({providedIn: 'root'})
export class AuthService {
  protected apiUrl = environment.apiUrl;

  private http = inject(HttpClient);
  private userService = inject(UserService);
  private router = inject(Router);

  login(username: string, password: string): Observable<object> {
    return this.http.post(`${this.apiUrl}/auth/login`, {username, password})
      .pipe(
        tap(() => {
          this.userService.loadUser();
        })
      );
  }

  logout(): Observable<object> {
    return this.http.post(`${this.apiUrl}/auth/logout`, {})
      .pipe(tap(() => {
        this.userService.clearUser()
        this.router.navigate(['/']);
      }));
  }


}
