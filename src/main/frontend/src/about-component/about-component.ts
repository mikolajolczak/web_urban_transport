import {Component, OnDestroy, OnInit} from '@angular/core';
import {TableViewModel} from '../views/table-view.model';
import {EmployeeMapperService} from '../services/employee-mapper.service';
import {faChevronRight, faSave, faTimes, faUserEdit} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {EmployeeService} from '../services/employee.service';
import {User, UserService} from '../services/user.service';
import {filter, map, Observable, of, Subject, switchMap, takeUntil} from 'rxjs';
import {AsyncPipe} from '@angular/common';
import {Employee} from '../entities/employee';
import {FormsModule} from '@angular/forms';
import {Office} from '../entities/office';
import {Position} from '../entities/position';
import {Salary} from '../entities/salary';
import {OfficeService} from '../services/office.service';
import {PositionService} from '../services/position.service';
import {SalaryService} from '../services/salary.service';

@Component({
  selector: 'app-about-component',
  templateUrl: './about-component.html',
  styleUrls: ['./about-component.scss'],
  imports: [FontAwesomeModule, AsyncPipe, FormsModule]
})
export class AboutComponent implements OnInit, OnDestroy {
  employeeView$!: Observable<TableViewModel[]>;
  currentEmployee: Employee | null = null;
  isEditMode = false;

  faChevronRight = faChevronRight;
  faUserEdit = faUserEdit;
  faSave = faSave;
  faTimes = faTimes;

  offices: Office[] = [];
  positions: Position[] = [];
  salaries: Salary[] = [];

  editForm = {
    firstName: '',
    lastName: '',
    gender: '',
    personalIdentificationNumber: '',
    accountNumber: '',
    employmentDate: '',
    officeId: 0,
    positionId: 0,
    salaryId: 0,
    street: '',
    city: '',
    buildingNumber: '',
    apartmentNumber: '',
    postalCode: '',
  };

  private destroy$ = new Subject<void>();

  constructor(
    private mapper: EmployeeMapperService,
    private employeeService: EmployeeService,
    private userService: UserService,
    private officeService: OfficeService,
    private positionService: PositionService,
    private salaryService: SalaryService
  ) {
  }

  ngOnInit(): void {
    this.loadOffices();
    this.loadPositions();
    this.loadSalaries();

    this.employeeView$ = this.userService.user$.pipe(
      filter((user: User | null): user is User => !!user && !!user.employeeId),
      switchMap(user => {
        if (user.role === 'ADMIN') {
          const mockEmployee: Employee = {
            id: 0,
            firstName: "ADMIN",
            lastName: "ADMIN",
            gender: "ADMIN",
            employmentDate: "ADMIN",
            office: {
              id: 0,
              officeName: "ADMIN"
            },
            position: {
              id: 0,
              positionName: "ADMIN"
            },
            salary: {
              id: 0,
              amount: 0
            },
            address: {
              id: 0,
              street: "ADMIN",
              city: "ADMIN",
              buildingNumber: "ADMIN",
              postalCode: "ADMIN"
            }
          };
          return of(mockEmployee);
        }
        return this.employeeService.getById(user.employeeId!);
      }),
      map(employee => {
        this.currentEmployee = employee;
        this.initEditForm(employee);
        return this.mapper.mapToView(employee);
      }),
      takeUntil(this.destroy$)
    );
  }

  private loadOffices(): void {
    this.officeService.getAll().subscribe({
      next: offices => {
        this.offices = offices;
      },
      error: err => console.error(err)
    });
  }

  private loadPositions(): void {
    this.positionService.getAll().subscribe({
      next: positions => {
        this.positions = positions;
      },
      error: err => console.error(err)
    });
  }

  private loadSalaries(): void {
    this.salaryService.getAll().subscribe({
      next: salaries => {
        this.salaries = salaries;
      },
      error: err => console.error(err)
    });
  }

  private initEditForm(employee: Employee): void {
    this.editForm = {
      firstName: employee.firstName,
      lastName: employee.lastName,
      gender: employee.gender,
      personalIdentificationNumber: employee.personalIdentificationNumber || '',
      accountNumber: employee.accountNumber || '',
      employmentDate: employee.employmentDate,
      officeId: employee.office.id,
      positionId: employee.position.id,
      salaryId: employee.salary.id,
      street: employee.address.street,
      city: employee.address.city,
      buildingNumber: employee.address.buildingNumber,
      apartmentNumber: employee.address.apartmentNumber || '',
      postalCode: employee.address.postalCode
    };
  }

  enterEditMode(): void {
    if (this.currentEmployee) {
      this.initEditForm(this.currentEmployee);
      this.isEditMode = true;
    }
  }

  cancelEdit(): void {
    this.isEditMode = false;
    if (this.currentEmployee) {
      this.initEditForm(this.currentEmployee);
    }
  }

  saveChanges(): void {
    if (!this.currentEmployee) return;

    const selectedOffice = this.offices.find(o => o.id === this.editForm.officeId);
    const selectedPosition = this.positions.find(p => p.id === this.editForm.positionId);
    const selectedSalary = this.salaries.find(s => s.id === this.editForm.salaryId);

    const updatedEmployee: Employee = {
      id: this.currentEmployee.id,
      firstName: this.editForm.firstName,
      lastName: this.editForm.lastName,
      gender: this.editForm.gender,
      personalIdentificationNumber: this.editForm.personalIdentificationNumber,
      accountNumber: this.editForm.accountNumber,
      employmentDate: this.editForm.employmentDate,
      office: {
        id: this.editForm.officeId,
        officeName: selectedOffice?.officeName || ''
      },
      position: {
        id: this.editForm.positionId,
        positionName: selectedPosition?.positionName || ''
      },
      salary: {
        id: this.editForm.salaryId,
        amount: selectedSalary?.amount || 0
      },
      address: {
        id: this.currentEmployee.address.id,
        street: this.editForm.street,
        city: this.editForm.city,
        buildingNumber: this.editForm.buildingNumber,
        apartmentNumber: this.editForm.apartmentNumber,
        postalCode: this.editForm.postalCode
      }
    };

    this.employeeService.update(updatedEmployee.id, updatedEmployee).subscribe({
      next: (response) => {
        this.currentEmployee = response;
        this.isEditMode = false;
        this.employeeView$ = of(this.mapper.mapToView(response));
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
