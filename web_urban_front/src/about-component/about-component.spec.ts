import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AboutComponent } from './about-component';
import { EmployeeMapperService } from '../services/employee-mapper.service';
import { EmployeeService } from '../services/employee.service';
import { UserService } from '../services/user.service';
import { OfficeService } from '../services/office.service';
import { PositionService } from '../services/position.service';
import { SalaryService } from '../services/salary.service';
import { of, throwError, BehaviorSubject } from 'rxjs';
import { Employee } from '../entities/employee';
import { User } from '../services/user.service';
import { Office } from '../entities/office';
import { Position } from '../entities/position';
import { Salary } from '../entities/salary';
import { TableViewModel } from '../views/table-view.model';
import {provideHttpClientTesting} from '@angular/common/http/testing';

describe('AboutComponent', () => {
  let component: AboutComponent;
  let fixture: ComponentFixture<AboutComponent>;
  let mockEmployeeService: jasmine.SpyObj<EmployeeService>;
  let mockUserService: jasmine.SpyObj<UserService>;
  let mockOfficeService: jasmine.SpyObj<OfficeService>;
  let mockPositionService: jasmine.SpyObj<PositionService>;
  let mockSalaryService: jasmine.SpyObj<SalaryService>;
  let mockMapper: jasmine.SpyObj<EmployeeMapperService>;
  let userSubject: BehaviorSubject<User | null>;

  const mockEmployee: Employee = {
    id: 1,
    firstName: 'John',
    lastName: 'Doe',
    gender: 'Male',
    personalIdentificationNumber: '12345678901',
    accountNumber: '1234567890',
    employmentDate: '2020-01-01',
    office: {
      id: 1,
      officeName: 'Main Office'
    },
    position: {
      id: 1,
      positionName: 'Developer'
    },
    salary: {
      id: 1,
      amount: 5000
    },
    address: {
      id: 1,
      street: 'Main Street',
      city: 'New York',
      buildingNumber: '10',
      apartmentNumber: '5A',
      postalCode: '10001'
    }
  };

  const mockOffices: Office[] = [
    { id: 1, officeName: 'Main Office' },
    { id: 2, officeName: 'Branch Office' }
  ];

  const mockPositions: Position[] = [
    { id: 1, positionName: 'Developer' },
    { id: 2, positionName: 'Manager' }
  ];

  const mockSalaries: Salary[] = [
    { id: 1, amount: 5000 },
    { id: 2, amount: 7000 }
  ];

  const mockTableViewModel: TableViewModel[] = [
    { label: 'First Name', value: 'John' },
    { label: 'Last Name', value: 'Doe' }
  ];

  beforeEach(async () => {
    userSubject = new BehaviorSubject<User | null>(null);

    mockEmployeeService = jasmine.createSpyObj('EmployeeService', ['getById', 'update']);
    mockUserService = jasmine.createSpyObj('UserService', [], { user$: userSubject.asObservable() });
    mockOfficeService = jasmine.createSpyObj('OfficeService', ['getAll']);
    mockPositionService = jasmine.createSpyObj('PositionService', ['getAll']);
    mockSalaryService = jasmine.createSpyObj('SalaryService', ['getAll']);
    mockMapper = jasmine.createSpyObj('EmployeeMapperService', ['mapToView']);

    await TestBed.configureTestingModule({
      imports: [AboutComponent],
      providers: [
        { provide: EmployeeService, useValue: mockEmployeeService },
        { provide: UserService, useValue: mockUserService },
        { provide: OfficeService, useValue: mockOfficeService },
        { provide: PositionService, useValue: mockPositionService },
        { provide: SalaryService, useValue: mockSalaryService },
        { provide: EmployeeMapperService, useValue: mockMapper },provideHttpClientTesting()
      ]
    }).compileComponents();

    mockOfficeService.getAll.and.returnValue(of(mockOffices));
    mockPositionService.getAll.and.returnValue(of(mockPositions));
    mockSalaryService.getAll.and.returnValue(of(mockSalaries));
    mockEmployeeService.getById.and.returnValue(of(mockEmployee));
    mockMapper.mapToView.and.returnValue(mockTableViewModel);

    fixture = TestBed.createComponent(AboutComponent);
    component = fixture.componentInstance;
  });

  afterEach(() => {
    userSubject.complete();
  });

  describe('Component Initialization', () => {
    it('should create the component', () => {
      expect(component).toBeTruthy();
    });

    it('should initialize with edit mode disabled', () => {
      expect(component.isEditMode).toBeFalse();
    });

    it('should initialize with null current employee', () => {
      expect(component.currentEmployee).toBeNull();
    });

    it('should initialize font awesome icons', () => {
      expect(component.faChevronRight).toBeDefined();
      expect(component.faUserEdit).toBeDefined();
      expect(component.faSave).toBeDefined();
      expect(component.faTimes).toBeDefined();
    });
  });

  describe('ngOnInit', () => {
    it('should load offices, positions, and salaries on initialization', () => {
      fixture.detectChanges();

      expect(mockOfficeService.getAll).toHaveBeenCalled();
      expect(mockPositionService.getAll).toHaveBeenCalled();
      expect(mockSalaryService.getAll).toHaveBeenCalled();
      expect(component.offices).toEqual(mockOffices);
      expect(component.positions).toEqual(mockPositions);
      expect(component.salaries).toEqual(mockSalaries);
    });

    it('should handle office loading error', () => {
      const consoleErrorSpy = spyOn(console, 'error');
      const error = new Error('Office loading failed');
      mockOfficeService.getAll.and.returnValue(throwError(() => error));

      fixture.detectChanges();

      expect(consoleErrorSpy).toHaveBeenCalledWith(error);
    });

    it('should handle position loading error', () => {
      const consoleErrorSpy = spyOn(console, 'error');
      const error = new Error('Position loading failed');
      mockPositionService.getAll.and.returnValue(throwError(() => error));

      fixture.detectChanges();

      expect(consoleErrorSpy).toHaveBeenCalledWith(error);
    });

    it('should handle salary loading error', () => {
      const consoleErrorSpy = spyOn(console, 'error');
      const error = new Error('Salary loading failed');
      mockSalaryService.getAll.and.returnValue(throwError(() => error));

      fixture.detectChanges();

      expect(consoleErrorSpy).toHaveBeenCalledWith(error);
    });

    it('should not emit when user is null', (done) => {
      fixture.detectChanges();

      component.employeeView$.subscribe({
        next: () => fail('Should not emit for null user'),
        error: () => fail('Should not error'),
        complete: () => done()
      });

      userSubject.next(null);
      userSubject.complete();
    });

    it('should not emit when user has no employeeId', (done) => {
      fixture.detectChanges();

      component.employeeView$.subscribe({
        next: () => fail('Should not emit for user without employeeId'),
        error: () => fail('Should not error'),
        complete: () => done()
      });

      userSubject.next({ username: 'user', role: 'USER', employeeId: null } as User);
      userSubject.complete();
    });

    it('should load employee data for regular user', (done) => {
      const user: User = { username: 'user', role: 'USER', employeeId: 1 };

      fixture.detectChanges();

      component.employeeView$.subscribe(view => {
        expect(mockEmployeeService.getById).toHaveBeenCalledWith(1);
        expect(component.currentEmployee).toEqual(mockEmployee);
        expect(mockMapper.mapToView).toHaveBeenCalledWith(mockEmployee);
        expect(view).toEqual(mockTableViewModel);
        done();
      });

      userSubject.next(user);
    });

    it('should return mock admin employee for admin user', (done) => {
      const adminUser: User = { username: 'admin', role: 'ADMIN', employeeId: 1 };

      fixture.detectChanges();

      component.employeeView$.subscribe(_ => {
        expect(mockEmployeeService.getById).not.toHaveBeenCalled();
        expect(component.currentEmployee?.firstName).toBe('ADMIN');
        expect(component.currentEmployee?.lastName).toBe('ADMIN');
        expect(mockMapper.mapToView).toHaveBeenCalled();
        done();
      });

      userSubject.next(adminUser);
    });

    it('should initialize edit form with employee data', (done) => {
      const user: User = { username: 'user', role: 'USER', employeeId: 1 };

      fixture.detectChanges();

      component.employeeView$.subscribe(() => {
        expect(component.editForm.firstName).toBe('John');
        expect(component.editForm.lastName).toBe('Doe');
        expect(component.editForm.gender).toBe('Male');
        expect(component.editForm.personalIdentificationNumber).toBe('12345678901');
        expect(component.editForm.accountNumber).toBe('1234567890');
        expect(component.editForm.employmentDate).toBe('2020-01-01');
        expect(component.editForm.officeId).toBe(1);
        expect(component.editForm.positionId).toBe(1);
        expect(component.editForm.salaryId).toBe(1);
        expect(component.editForm.street).toBe('Main Street');
        expect(component.editForm.city).toBe('New York');
        expect(component.editForm.buildingNumber).toBe('10');
        expect(component.editForm.apartmentNumber).toBe('5A');
        expect(component.editForm.postalCode).toBe('10001');
        done();
      });

      userSubject.next(user);
    });

    it('should handle employee with missing optional fields', (done) => {
      const employeeWithoutOptionals: Employee = {
        ...mockEmployee,
        personalIdentificationNumber: undefined,
        accountNumber: undefined,
        address: {
          ...mockEmployee.address,
          apartmentNumber: undefined
        }
      };

      mockEmployeeService.getById.and.returnValue(of(employeeWithoutOptionals));
      const user: User = { username: 'user', role: 'USER', employeeId: 1 };

      fixture.detectChanges();

      component.employeeView$.subscribe(() => {
        expect(component.editForm.personalIdentificationNumber).toBe('');
        expect(component.editForm.accountNumber).toBe('');
        expect(component.editForm.apartmentNumber).toBe('');
        done();
      });

      userSubject.next(user);
    });
  });

  describe('enterEditMode', () => {
    beforeEach(() => {
      component.currentEmployee = mockEmployee;
    });

    it('should enable edit mode', () => {
      component.enterEditMode();
      expect(component.isEditMode).toBeTrue();
    });

    it('should reinitialize edit form with current employee data', () => {
      component.editForm.firstName = 'Modified';
      component.enterEditMode();
      expect(component.editForm.firstName).toBe('John');
    });

    it('should not enable edit mode when current employee is null', () => {
      component.currentEmployee = null;
      component.enterEditMode();
      expect(component.isEditMode).toBeFalse();
    });
  });

  describe('cancelEdit', () => {
    beforeEach(() => {
      component.currentEmployee = mockEmployee;
      component.isEditMode = true;
    });

    it('should disable edit mode', () => {
      component.cancelEdit();
      expect(component.isEditMode).toBeFalse();
    });

    it('should reset edit form to current employee data', () => {
      component.editForm.firstName = 'Modified';
      component.cancelEdit();
      expect(component.editForm.firstName).toBe('John');
    });

    it('should not reset form when current employee is null', () => {
      component.currentEmployee = null;
      component.editForm.firstName = 'Modified';
      component.cancelEdit();
      expect(component.editForm.firstName).toBe('Modified');
    });
  });

  describe('saveChanges', () => {
    beforeEach(() => {
      component.currentEmployee = mockEmployee;
      component.offices = mockOffices;
      component.positions = mockPositions;
      component.salaries = mockSalaries;
      component.isEditMode = true;
    });

    it('should not proceed if current employee is null', () => {
      component.currentEmployee = null;
      component.saveChanges();
      expect(mockEmployeeService.update).not.toHaveBeenCalled();
    });

    it('should update employee with modified data', () => {
      component.editForm.firstName = 'Jane';
      component.editForm.officeId = 2;

      const updatedEmployee: Employee = {
        ...mockEmployee,
        firstName: 'Jane',
        office: { id: 2, officeName: 'Branch Office' }
      };

      mockEmployeeService.update.and.returnValue(of(updatedEmployee));

      component.saveChanges();

      expect(mockEmployeeService.update).toHaveBeenCalledWith(
        1,
        jasmine.objectContaining({
          id: 1,
          firstName: 'Jane',
          office: jasmine.objectContaining({ id: 2 })
        })
      );
    });

    it('should disable edit mode on successful update', () => {
      mockEmployeeService.update.and.returnValue(of(mockEmployee));

      component.saveChanges();

      expect(component.isEditMode).toBeFalse();
    });

    it('should update current employee on successful save', () => {
      const updatedEmployee = { ...mockEmployee, firstName: 'Jane' };
      mockEmployeeService.update.and.returnValue(of(updatedEmployee));

      component.saveChanges();

      expect(component.currentEmployee).toEqual(updatedEmployee);
    });

    it('should update employee view observable on successful save', (done) => {
      mockEmployeeService.update.and.returnValue(of(mockEmployee));

      component.saveChanges();

      component.employeeView$.subscribe(view => {
        expect(mockMapper.mapToView).toHaveBeenCalledWith(mockEmployee);
        expect(view).toEqual(mockTableViewModel);
        done();
      });
    });

    it('should handle update error', () => {
      const consoleErrorSpy = spyOn(console, 'error');
      const error = new Error('Update failed');
      mockEmployeeService.update.and.returnValue(throwError(() => error));

      component.saveChanges();

      expect(consoleErrorSpy).toHaveBeenCalledWith(error);
    });

    it('should not disable edit mode on update error', () => {
      mockEmployeeService.update.and.returnValue(throwError(() => new Error('Update failed')));

      component.saveChanges();

      expect(component.isEditMode).toBeTrue();
    });

    it('should build updated employee with all address fields', () => {
      component.editForm = {
        firstName: 'Jane',
        lastName: 'Smith',
        gender: 'Female',
        personalIdentificationNumber: '98765432109',
        accountNumber: '0987654321',
        employmentDate: '2021-05-15',
        officeId: 2,
        positionId: 2,
        salaryId: 2,
        street: 'Second Street',
        city: 'Boston',
        buildingNumber: '20',
        apartmentNumber: '10B',
        postalCode: '02101'
      };

      mockEmployeeService.update.and.returnValue(of(mockEmployee));

      component.saveChanges();

      expect(mockEmployeeService.update).toHaveBeenCalledWith(
        1,
        jasmine.objectContaining({
          address: jasmine.objectContaining({
            id: 1,
            street: 'Second Street',
            city: 'Boston',
            buildingNumber: '20',
            apartmentNumber: '10B',
            postalCode: '02101'
          })
        })
      );
    });

    it('should use empty string for office name if office not found', () => {
      component.editForm.officeId = 999;
      mockEmployeeService.update.and.returnValue(of(mockEmployee));

      component.saveChanges();

      expect(mockEmployeeService.update).toHaveBeenCalledWith(
        1,
        jasmine.objectContaining({
          office: jasmine.objectContaining({
            id: 999,
            officeName: ''
          })
        })
      );
    });

    it('should use empty string for position name if position not found', () => {
      component.editForm.positionId = 999;
      mockEmployeeService.update.and.returnValue(of(mockEmployee));

      component.saveChanges();

      expect(mockEmployeeService.update).toHaveBeenCalledWith(
        1,
        jasmine.objectContaining({
          position: jasmine.objectContaining({
            id: 999,
            positionName: ''
          })
        })
      );
    });

    it('should use zero for salary amount if salary not found', () => {
      component.editForm.salaryId = 999;
      mockEmployeeService.update.and.returnValue(of(mockEmployee));

      component.saveChanges();

      expect(mockEmployeeService.update).toHaveBeenCalledWith(
        1,
        jasmine.objectContaining({
          salary: jasmine.objectContaining({
            id: 999,
            amount: 0
          })
        })
      );
    });
  });

  describe('ngOnDestroy', () => {
    it('should complete destroy subject', () => {
      const destroySpy = spyOn(component['destroy$'], 'next');
      const completeSpy = spyOn(component['destroy$'], 'complete');

      component.ngOnDestroy();

      expect(destroySpy).toHaveBeenCalled();
      expect(completeSpy).toHaveBeenCalled();
    });

    it('should unsubscribe from all subscriptions', (done) => {
      const user: User = { username: 'user', role: 'USER', employeeId: 1 };
      fixture.detectChanges();

      let emissionCount = 0;
      component.employeeView$.subscribe(() => {
        emissionCount++;
      });

      userSubject.next(user);

      component.ngOnDestroy();

      userSubject.next(user);

      setTimeout(() => {
        expect(emissionCount).toBe(1);
        done();
      }, 100);
    });
  });

  describe('Integration Tests', () => {
    it('should complete full edit workflow', (done) => {
      const user: User = { username: 'user', role: 'USER', employeeId: 1 };
      fixture.detectChanges();

      component.employeeView$.subscribe(() => {
        expect(component.currentEmployee).toEqual(mockEmployee);

        component.enterEditMode();
        expect(component.isEditMode).toBeTrue();

        component.editForm.firstName = 'Modified';

        const updatedEmployee = { ...mockEmployee, firstName: 'Modified' };
        mockEmployeeService.update.and.returnValue(of(updatedEmployee));

        component.saveChanges();

        expect(component.isEditMode).toBeFalse();
        expect(component.currentEmployee?.firstName).toBe('Modified');
        done();
      });

      userSubject.next(user);
    });

    it('should handle edit cancellation workflow', (done) => {
      const user: User = { username: 'user', role: 'USER', employeeId: 1 };
      fixture.detectChanges();

      component.employeeView$.subscribe(() => {
        component.enterEditMode();
        component.editForm.firstName = 'Modified';

        component.cancelEdit();

        expect(component.isEditMode).toBeFalse();
        expect(component.editForm.firstName).toBe('John');
        done();
      });

      userSubject.next(user);
    });
  });
});
