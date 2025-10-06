import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Header } from './header';
import { AuthService } from '../../services/auth.service';
import { UserService, UserRole } from '../../services/user.service';
import { of } from 'rxjs';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { provideRouter } from '@angular/router';

describe('Header', () => {
  let component: Header;
  let fixture: ComponentFixture<Header>;
  let mockAuthService: Partial<AuthService>;
  let mockUserService: Partial<UserService>;

  beforeEach(async () => {
    mockAuthService = {
      logout: () => of(Object)
    };

    mockUserService = {
      isLoggedIn$: of(true),
      role$: of('ADMIN' as UserRole)
    };

    await TestBed.configureTestingModule({
      imports: [FontAwesomeModule, Header],
      providers: [
        { provide: AuthService, useValue: mockAuthService },
        { provide: UserService, useValue: mockUserService },
        provideRouter([])
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(Header);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize isLoggedIn$ and role$', (done) => {
    component.isLoggedIn$.subscribe(value => {
      expect(value).toBe(true);
    });

    component.role$.subscribe(role => {
      expect(role).toBe('ADMIN');
      done();
    });
  });

  it('should call authService.logout when tryLogout is invoked', () => {
    spyOn(component['authService'], 'logout').and.returnValue(of(Object));
    component.tryLogout();
    expect(component['authService'].logout).toHaveBeenCalled();
  });

  it('should correctly check roles', () => {
    expect(component.hasRole(['ADMIN'], 'ADMIN')).toBe(true);
    expect(component.hasRole(['USER'], 'ADMIN')).toBe(false);
    expect(component.hasRole(['ADMIN'], null)).toBe(false);
  });

  it('should have all tabs defined', () => {
    expect(component.tabs.length).toBe(5);
    expect(component.tabs.map(t => t.name)).toEqual([
      'Employees',
      'Vehicles',
      'Stops',
      'Offices',
      'About'
    ]);
  });

  it('faChevronRight should be defined', () => {
    expect(component.faChevronRight).toBeDefined();
  });

  it('should display correct tabs for ADMIN', () => {
    component.role$ = of('ADMIN');
    fixture.detectChanges();

    component.tabs.forEach(tab => {
      if (tab.roles.includes('ADMIN')) {
        expect(component.hasRole(tab.roles, 'ADMIN')).toBe(true);
      }
    });
  });

  it('should display correct tabs for USER', () => {
    component.role$ = of('USER');
    fixture.detectChanges();

    component.tabs.forEach(tab => {
      if (tab.roles.includes('USER')) {
        expect(component.hasRole(tab.roles, 'USER')).toBe(true);
      } else {
        expect(component.hasRole(tab.roles, 'USER')).toBe(false);
      }
    });
  });
});
