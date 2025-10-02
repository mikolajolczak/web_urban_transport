import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Footer } from './footer';
import { DebugElement } from '@angular/core';

describe('Footer Component', () => {
  let component: Footer;
  let fixture: ComponentFixture<Footer>;
  let debugElement: DebugElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Footer]
    }).compileComponents();

    fixture = TestBed.createComponent(Footer);
    component = fixture.componentInstance;
    debugElement = fixture.debugElement;
    fixture.detectChanges();
  });

  it('should create the footer component', () => {
    expect(component).toBeTruthy();
  });

  it('should render the footer text', () => {
    const footerText = debugElement.query(By.css('div'));
    expect(footerText).toBeTruthy();
  });

});
