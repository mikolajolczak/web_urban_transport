import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditVehicleModal } from './edit-vehicle-modal';

describe('EditVehicleModal', () => {
  let component: EditVehicleModal;
  let fixture: ComponentFixture<EditVehicleModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditVehicleModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditVehicleModal);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
