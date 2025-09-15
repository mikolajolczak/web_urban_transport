import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EditOfficeModal} from './edit-office-modal';

describe('EditOfficeModal', () => {
  let component: EditOfficeModal;
  let fixture: ComponentFixture<EditOfficeModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditOfficeModal]
    })
      .compileComponents();

    fixture = TestBed.createComponent(EditOfficeModal);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
