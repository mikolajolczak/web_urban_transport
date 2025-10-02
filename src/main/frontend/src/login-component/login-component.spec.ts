import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponent } from './login-component';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authServiceSpy: jasmine.SpyObj<AuthService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    const authSpy = jasmine.createSpyObj('AuthService', ['login']);
    const routeSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [FormsModule, FontAwesomeModule,LoginComponent],
      providers: [
        { provide: AuthService, useValue: authSpy },
        { provide: Router, useValue: routeSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authServiceSpy = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;
    routerSpy = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should have FontAwesome icons assigned', () => {
    expect(component.faChevronRight).toBeDefined();
    expect(component.faRightToBracket).toBeDefined();
  });

  it('should call authService.login with login and password on tryLogin', () => {
    component.login = 'testuser';
    component.password = 'testpass';
    authServiceSpy.login.and.returnValue(of({}));

    component.tryLogin();

    expect(authServiceSpy.login).toHaveBeenCalledWith('testuser', 'testpass');
  });

  it('should navigate to "/" on successful login', () => {
    authServiceSpy.login.and.returnValue(of({}));
    component.login = 'user';
    component.password = 'pass';

    component.tryLogin();

    expect(routerSpy.navigate).toHaveBeenCalledWith(['/']);
  });

  it('should handle login error without navigation', () => {
    spyOn(console, 'error');
    authServiceSpy.login.and.returnValue(throwError(() => new Error('Failed')));
    component.login = 'user';
    component.password = 'wrongpass';

    component.tryLogin();

    expect(console.error).toHaveBeenCalledWith('Login failed');
    expect(routerSpy.navigate).not.toHaveBeenCalled();
  });

  it('should not call login if login or password is empty', () => {
    component.login = '';
    component.password = '';
    component.tryLogin();
    expect(authServiceSpy.login).not.toHaveBeenCalled();
  });
});
