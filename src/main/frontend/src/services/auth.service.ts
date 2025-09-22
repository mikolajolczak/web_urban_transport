import {Injectable} from '@angular/core';
import {tap} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../environments/environment';
import {UserService} from './user.service';
import {Router} from '@angular/router';

@Injectable({providedIn: 'root'})
export class AuthService {
  protected apiUrl = environment.apiUrl;


  constructor(private http: HttpClient, private userService: UserService, private router:Router) {
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/auth/login`, {username, password})
      .pipe(
        tap(() => {
          this.userService.loadUser();
        })
      );
  }

  logout(): Observable<any> {
    return this.http.post(`${this.apiUrl}/auth/logout`, {})
      .pipe(tap(() => {
        this.userService.clearUser()
        this.router.navigate(['/']);
      }));
  }


}
