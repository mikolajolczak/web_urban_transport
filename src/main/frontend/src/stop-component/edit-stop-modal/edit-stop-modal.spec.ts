import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EditStopModal } from './edit-stop-modal';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormsModule } from '@angular/forms';

describe('EditStopModal', () => {
  let component: EditStopModal;
  let fixture: ComponentFixture<EditStopModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FontAwesomeModule, FormsModule, EditStopModal],
    }).compileComponents();

    fixture = TestBed.createComponent(EditStopModal);
    component = fixture.componentInstance;
    component.stop = { id: 1, stopName: 'Test Stop' };
    component.isEditMode = false;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return modal title based on isEditMode', () => {
    component.isEditMode = true;
    expect(component.modalTitle).toBe('Edit Stop');
    component.isEditMode = false;
    expect(component.modalTitle).toBe('Create New Stop');
  });

  it('should return save button text based on isEditMode', () => {
    component.isEditMode = true;
    expect(component.saveButtonText).toBe('Update');
    component.isEditMode = false;
    expect(component.saveButtonText).toBe('Create');
  });

  describe('address getters and setters', () => {
    it('should set and get city', () => {
      component.city = 'New York';
      expect(component.city).toBe('New York');
      expect(component.stop.address?.city).toBe('New York');
    });

    it('should set and get street', () => {
      component.street = 'Broadway';
      expect(component.street).toBe('Broadway');
      expect(component.stop.address?.street).toBe('Broadway');
    });

    it('should set and get buildingNumber', () => {
      component.buildingNumber = '42B';
      expect(component.buildingNumber).toBe('42B');
      expect(component.stop.address?.buildingNumber).toBe('42B');
    });

    it('should set and get apartmentNumber', () => {
      component.apartmentNumber = '101';
      expect(component.apartmentNumber).toBe('101');
      expect(component.stop.address?.apartmentNumber).toBe('101');
    });

    it('should set and get postalCode', () => {
      component.postalCode = '10001';
      expect(component.postalCode).toBe('10001');
      expect(component.stop.address?.postalCode).toBe('10001');
    });

    it('should initialize address if null when setting properties', () => {
      component.city = 'Chicago';
      expect(component.stop.address).toBeTruthy();
      expect(component.stop.address?.city).toBe('Chicago');
    });
  });

  describe('save', () => {
    it('should emit createStop and closed when not in edit mode', () => {
      spyOn(component.createStop, 'emit');
      spyOn(component.closed, 'emit');
      component.save();
      expect(component.createStop.emit).toHaveBeenCalledWith(component.stop);
      expect(component.closed.emit).toHaveBeenCalled();
    });

    it('should emit updateStop and closed when in edit mode', () => {
      component.isEditMode = true;
      spyOn(component.updateStop, 'emit');
      spyOn(component.closed, 'emit');
      component.save();
      expect(component.updateStop.emit).toHaveBeenCalledWith(component.stop);
      expect(component.closed.emit).toHaveBeenCalled();
    });
  });

  it('should have FontAwesome icons defined', () => {
    expect(component.faChevronRight).toBeDefined();
    expect(component.faFloppyDisk).toBeDefined();
  });
});
