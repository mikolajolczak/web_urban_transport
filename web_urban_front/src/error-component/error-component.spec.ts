import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ErrorComponent } from './error-component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ActivatedRoute } from '@angular/router';

describe('ErrorComponent', () => {
  let component: ErrorComponent;
  let fixture: ComponentFixture<ErrorComponent>;

  function createComponentWithRouteParam(code: string | null) {
    TestBed.configureTestingModule({
      imports: [FontAwesomeModule, ErrorComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get: () => code
              }
            }
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ErrorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }

  it('should create the component', () => {
    createComponentWithRouteParam(null);
    expect(component).toBeTruthy();
  });

  it('should set code to default 500 if route param is null', () => {
    createComponentWithRouteParam(null);
    expect(component.code).toBe('500');
  });

  it('should set code to default 500 if route param is invalid', () => {
    createComponentWithRouteParam('999');
    expect(component.code).toBe('500');
  });

  it('should set code to route param if it exists in errorDescriptions', () => {
    createComponentWithRouteParam('404');
    expect(component.code).toBe('404');
  });

  it('should have faXmark defined', () => {
    createComponentWithRouteParam(null);
    expect(component.faXmark).toBeDefined();
  });

  it('should have errorDescriptions object defined', () => {
    createComponentWithRouteParam(null);
    expect(component.errorDescriptions).toBeDefined();
    expect(component.errorDescriptions['404']).toBe('The page you are looking for does not exist or has been moved.');
    expect(component.errorDescriptions['403']).toBe('You do not have permission to access this page.');
    expect(component.errorDescriptions['500']).toBe('An internal server error occurred. Please try again later.');
  });
});
