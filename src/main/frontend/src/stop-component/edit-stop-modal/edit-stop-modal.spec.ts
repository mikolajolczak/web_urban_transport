import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditStopModal } from './edit-stop-modal';

describe('EditStopModal', () => {
  let component: EditStopModal;
  let fixture: ComponentFixture<EditStopModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditStopModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditStopModal);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
