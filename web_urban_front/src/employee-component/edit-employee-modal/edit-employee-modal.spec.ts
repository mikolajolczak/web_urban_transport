import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { of, throwError } from 'rxjs';
import { EditEmployeeModal } from './edit-employee-modal';
import { OfficeService } from '../../services/office.service';
import { PositionService } from '../../services/position.service';
import { SalaryService } from '../../services/salary.service';
import { Employee } from '../../entities/employee';
import { Office } from '../../entities/office';
import { Position } from '../../entities/position';
import { Salary } from '../../entities/salary';

describe('EditEmployeeModal', () => {
  let component: EditEmployeeModal;
  let fixture: ComponentFixture<EditEmployeeModal>;
  let officeServiceSpy: jasmine.SpyObj<OfficeService>;
  let positionServiceSpy: jasmine.SpyObj<PositionService>;
  let salaryServiceSpy: jasmine.SpyObj<SalaryService>;

  beforeEach(async () => {
    officeServiceSpy = jasmine.createSpyObj('OfficeService', ['getAll']);
    positionServiceSpy = jasmine.createSpyObj('PositionService', ['getAll']);
    salaryServiceSpy = jasmine.createSpyObj('SalaryService', ['getAll']);

    await TestBed.configureTestingModule({
      imports: [FormsModule, FontAwesomeModule, EditEmployeeModal],
      providers: [
        { provide: OfficeService, useValue: officeServiceSpy },
        { provide: PositionService, useValue: positionServiceSpy },
        { provide: SalaryService, useValue: salaryServiceSpy },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(EditEmployeeModal);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('employee input', () => {
    it('should load offices, positions, and salaries when employee is set', () => {
      const offices: Office[] = [{ id: 1, officeName: 'HQ' }];
      const positions: Position[] = [{ id: 1, positionName: 'Dev' }];
      const salaries: Salary[] = [{ id: 1, amount: 1000 }];
      officeServiceSpy.getAll.and.returnValue(of(offices));
      positionServiceSpy.getAll.and.returnValue(of(positions));
      salaryServiceSpy.getAll.and.returnValue(of(salaries));

      component.employee = {id: 1, firstName: 'John'} as Employee;

      expect(component.offices).toEqual(offices);
      expect(component.positions).toEqual(positions);
      expect(component.salaries).toEqual(salaries);
    });

    it('should handle null employee without loading data', () => {
      component.employee = null as any;

      expect(component.offices).toEqual([]);
      expect(component.positions).toEqual([]);
      expect(component.salaries).toEqual([]);
    });
  });

  describe('loadOffices', () => {
    it('should populate offices on success', () => {
      const offices: Office[] = [{ id: 1, officeName: 'HQ' }];
      officeServiceSpy.getAll.and.returnValue(of(offices));

      component.loadOffices();

      expect(component.offices).toEqual(offices);
    });

    it('should log error on failure', () => {
      const error = new Error('Fail');
      officeServiceSpy.getAll.and.returnValue(throwError(() => error));
      spyOn(console, 'error');

      component.loadOffices();

      expect(console.error).toHaveBeenCalledWith(error);
    });
  });

  describe('loadPositions', () => {
    it('should populate positions on success', () => {
      const positions: Position[] = [{ id: 1, positionName: 'Dev' }];
      positionServiceSpy.getAll.and.returnValue(of(positions));

      component.loadPositions();

      expect(component.positions).toEqual(positions);
    });

    it('should log error on failure', () => {
      const error = new Error('Fail');
      positionServiceSpy.getAll.and.returnValue(throwError(() => error));
      spyOn(console, 'error');

      component.loadPositions();

      expect(console.error).toHaveBeenCalledWith(error);
    });
  });

  describe('loadSalaries', () => {
    it('should populate salaries on success', () => {
      const salaries: Salary[] = [{ id: 1, amount: 1000 }];
      salaryServiceSpy.getAll.and.returnValue(of(salaries));

      component.loadSalaries();

      expect(component.salaries).toEqual(salaries);
    });

    it('should log error on failure', () => {
      const error = new Error('Fail');
      salaryServiceSpy.getAll.and.returnValue(throwError(() => error));
      spyOn(console, 'error');

      component.loadSalaries();

      expect(console.error).toHaveBeenCalledWith(error);
    });
  });

  describe('save', () => {
    it('should emit updateEmployee and closed when in edit mode', () => {
      component.isEditMode = true;
      const mockEmployee: Employee = { id: 1, firstName: 'John' } as Employee;

      officeServiceSpy.getAll.and.returnValue(of([]));
      positionServiceSpy.getAll.and.returnValue(of([]));
      salaryServiceSpy.getAll.and.returnValue(of([]));

      spyOn(component.updateEmployee, 'emit');
      spyOn(component.closed, 'emit');

      component.employee = mockEmployee;
      component.save();

      expect(component.updateEmployee.emit).toHaveBeenCalledWith(mockEmployee);
      expect(component.closed.emit).toHaveBeenCalled();
    });

    it('should emit createEmployee and closed when not in edit mode', () => {
      component.isEditMode = false;

      officeServiceSpy.getAll.and.returnValue(of([]));
      positionServiceSpy.getAll.and.returnValue(of([]));
      salaryServiceSpy.getAll.and.returnValue(of([]));

      component.employee = { id: 1, firstName: 'John' } as Employee;

      spyOn(component.createEmployee, 'emit');
      spyOn(component.closed, 'emit');

      component.save();

      expect(component.createEmployee.emit).toHaveBeenCalledWith(component.employee);
      expect(component.closed.emit).toHaveBeenCalled();
    });
  });

  describe('modalTitle', () => {
    it('should return "Edit Office" in edit mode', () => {
      component.isEditMode = true;
      expect(component.modalTitle).toBe('Edit Office');
    });

    it('should return "Create New Office" in create mode', () => {
      component.isEditMode = false;
      expect(component.modalTitle).toBe('Create New Office');
    });
  });

  describe('saveButtonText', () => {
    it('should return "Update" in edit mode', () => {
      component.isEditMode = true;
      expect(component.saveButtonText).toBe('Update');
    });

    it('should return "Create" in create mode', () => {
      component.isEditMode = false;
      expect(component.saveButtonText).toBe('Create');
    });
  });
});
