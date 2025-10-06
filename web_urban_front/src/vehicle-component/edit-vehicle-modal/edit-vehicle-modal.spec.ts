import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EditVehicleModal } from './edit-vehicle-modal';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { OfficeService } from '../../services/office.service';
import { of, throwError } from 'rxjs';
import { Vehicle } from '../../entities/vehicle';
import { Office } from '../../entities/office';

describe('EditVehicleModal', () => {
  let component: EditVehicleModal;
  let fixture: ComponentFixture<EditVehicleModal>;
  let officeServiceSpy: jasmine.SpyObj<OfficeService>;

  const mockVehicle: Vehicle = { id: 1, model: 'Car', office: {id:1} } as Vehicle;
  const mockOffices: Office[] = [
    { id: 1, officeName: 'Office A' } as Office,
    { id: 2, officeName: 'Office B' } as Office,
  ];

  beforeEach(async () => {
    officeServiceSpy = jasmine.createSpyObj('OfficeService', ['getAll']);
    await TestBed.configureTestingModule({
      imports: [FormsModule, FontAwesomeModule, EditVehicleModal],
      providers: [{ provide: OfficeService, useValue: officeServiceSpy }]
    }).compileComponents();

    fixture = TestBed.createComponent(EditVehicleModal);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should load offices when vehicle is set', () => {
    officeServiceSpy.getAll.and.returnValue(of(mockOffices));
    component.vehicle = mockVehicle;
    expect(component.offices).toEqual(mockOffices);
    expect(officeServiceSpy.getAll).toHaveBeenCalled();
  });

  it('should handle officeService error', () => {
    spyOn(console, 'error');
    officeServiceSpy.getAll.and.returnValue(throwError(() => new Error('error')));
    component.vehicle = mockVehicle;
    expect(console.error).toHaveBeenCalledWith(jasmine.any(Error));
  });

  it('should emit createVehicle and closed when save in create mode', () => {
    component.isEditMode = false;

    officeServiceSpy.getAll.and.returnValue(of(mockOffices));

    component.vehicle = mockVehicle;

    spyOn(component.createVehicle, 'emit');
    spyOn(component.closed, 'emit');

    component.save();

    expect(component.createVehicle.emit).toHaveBeenCalledWith(mockVehicle);
    expect(component.closed.emit).toHaveBeenCalled();
  });

  it('should emit updateVehicle and closed when save in edit mode', () => {
    component.isEditMode = true;
    officeServiceSpy.getAll.and.returnValue(of(mockOffices));
    component.vehicle = mockVehicle;
    spyOn(component.updateVehicle, 'emit');
    spyOn(component.closed, 'emit');

    component.save();

    expect(component.updateVehicle.emit).toHaveBeenCalledWith(mockVehicle);
    expect(component.closed.emit).toHaveBeenCalled();
  });

  it('should return correct modal title for edit mode', () => {
    component.isEditMode = true;
    expect(component.modalTitle).toBe('Edit Vehicle');
  });

  it('should return correct modal title for create mode', () => {
    component.isEditMode = false;
    expect(component.modalTitle).toBe('Create New Vehicle');
  });

  it('should return correct save button text for edit mode', () => {
    component.isEditMode = true;
    expect(component.saveButtonText).toBe('Update');
  });

  it('should return correct save button text for create mode', () => {
    component.isEditMode = false;
    expect(component.saveButtonText).toBe('Create');
  });

  it('should initialize vehicle getter and setter correctly', () => {
    officeServiceSpy.getAll.and.returnValue(of(mockOffices));
    component.vehicle = mockVehicle;
    expect(component.vehicle).toEqual(mockVehicle);
    expect(component.offices).toEqual(mockOffices);
  });
});
