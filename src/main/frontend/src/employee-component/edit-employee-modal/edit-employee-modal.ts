import {Component, EventEmitter, inject, Input, Output} from '@angular/core';
import {faChevronRight, faFloppyDisk} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {FormsModule} from '@angular/forms';
import {Employee} from '../../entities/employee';
import {Office} from '../../entities/office';
import {Position} from '../../entities/position';
import {Salary} from '../../entities/salary';
import {OfficeService} from '../../services/office.service';
import {PositionService} from '../../services/position.service';
import {SalaryService} from '../../services/salary.service';

@Component({
  selector: 'app-edit-employee-modal',
  imports: [
    FontAwesomeModule, FormsModule
  ],
  templateUrl: './edit-employee-modal.html',
  styleUrl: './edit-employee-modal.scss'
})
export class EditEmployeeModal {
  private _employee!: Employee;
  @Input()
  set employee(value: Employee) {
    this._employee = value;
    if (value) {
      this.loadOffices()
      this.loadPositions()
      this.loadSalaries()
    }
  }

  get employee() {
    return this._employee;
  }

  @Input() isEditMode!: boolean;
  @Output() closed = new EventEmitter<void>();
  @Output() createEmployee = new EventEmitter<Employee>();
  @Output() updateEmployee = new EventEmitter<Employee>();

  private officeService = inject(OfficeService)
  private positionService = inject(PositionService)
  private salaryService = inject(SalaryService)

  faChevronRight = faChevronRight;
  faFloppyDisk = faFloppyDisk;

  offices: Office[] = [];

  loadOffices() {
    this.officeService.getAll().subscribe({
      next: offices => {
        this.offices = offices
      },
      error: err => console.error(err)
    });
  }

  positions: Position[] = []

  loadPositions() {
    this.positionService.getAll().subscribe({
      next: positions => {
        this.positions = positions
      },
      error: err => console.error(err)
    });
  }

  salaries: Salary[] = []


  loadSalaries() {
    this.salaryService.getAll().subscribe({
      next: salaries => {
        this.salaries = salaries
      },
      error: err => console.error(err)
    });
  }


  save() {
    if (this.isEditMode) {
      this.updateEmployee.emit(this.employee);
    } else {
      this.createEmployee.emit(this.employee);
    }
    this.closed.emit();
  }

  get modalTitle(): string {
    return this.isEditMode ? 'Edit Office' : 'Create New Office';
  }

  get saveButtonText(): string {
    return this.isEditMode ? 'Update' : 'Create';
  }


}
