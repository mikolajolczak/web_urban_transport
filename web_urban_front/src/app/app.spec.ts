import { ComponentFixture, TestBed } from '@angular/core/testing';
import { App } from './app';
import { UserService } from '../services/user.service';
import { Header } from './header/header';
import { Footer } from './footer/footer';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import {provideRouter} from '@angular/router';
import {HttpClientTestingModule, provideHttpClientTesting} from '@angular/common/http/testing';
import {HttpClient} from '@angular/common/http';

class MockUserService {
  loadUser() {
    return of({});
  }
}

describe('App', () => {
  let component: App;
  let fixture: ComponentFixture<App>;
  let userService: UserService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Header, Footer, App, HttpClientTestingModule],
      providers: [{ provide: UserService, useClass: MockUserService }, provideRouter([])]
    }).compileComponents();

    fixture = TestBed.createComponent(App);
    component = fixture.componentInstance;
    userService = TestBed.inject(UserService);
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should call loadUser on ngOnInit', () => {
    spyOn(userService, 'loadUser').and.callThrough();
    component.ngOnInit();
    expect(userService.loadUser).toHaveBeenCalled();
  });

  it('should call loadUser each time ngOnInit is called', () => {
    spyOn(userService, 'loadUser').and.callThrough();
    component.ngOnInit();
    component.ngOnInit();
    expect(userService.loadUser).toHaveBeenCalledTimes(2);
  });
});
