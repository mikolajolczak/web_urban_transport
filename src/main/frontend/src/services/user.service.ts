import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../environments/environment';

export type UserRole = 'ADMIN' | 'USER';

export interface User {
  role: UserRole;
  employeeId: number | null;
  username: string;
}

@Injectable({providedIn: 'root'})
export class UserService {
  private userSubject = new BehaviorSubject<User | null>(null);
  user$: Observable<User | null> = this.userSubject.asObservable();
  protected apiUrl = environment.apiUrl;
  constructor(private http: HttpClient) {
  }

  loadUser() {
    return this.http.get<User>(`${this.apiUrl}/auth/me`).subscribe({
      next: user => {
        this.userSubject.next(user)
      },
      error: () => {
        this.userSubject.next(null)
      }
    });
  }

  clearUser() {
    this.userSubject.next(null);
  }

  getRole(): UserRole | null {
    return this.userSubject.value?.role || null;
  }

  hasRole(expectedRole: string): boolean {
    return this.userSubject.value?.role === expectedRole;
  }
  isLoggedIn(){
    return !!this.userSubject.value;
  }
}
