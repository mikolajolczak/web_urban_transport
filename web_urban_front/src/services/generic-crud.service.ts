import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export abstract class GenericCrudService<T> {
  protected apiUrl = environment.apiUrl;
  protected abstract endpoint: string;
  protected http = inject(HttpClient);

  getAll(): Observable<T[]> {
    return this.http.get<T[]>(`${this.apiUrl}/${this.endpoint}`);
  }

  getById(id: number): Observable<T> {
    return this.http.get<T>(`${this.apiUrl}/${this.endpoint}/${id}`);
  }

  create(entity: T): Observable<T> {
    return this.http.post<T>(`${this.apiUrl}/${this.endpoint}`, entity);
  }

  update(id: number, entity: T): Observable<T> {
    return this.http.put<T>(`${this.apiUrl}/${this.endpoint}/${id}`, entity);
  }

  delete(id: number): Observable<object> {
    return this.http.delete(`${this.apiUrl}/${this.endpoint}/${id}`);
  }
}
