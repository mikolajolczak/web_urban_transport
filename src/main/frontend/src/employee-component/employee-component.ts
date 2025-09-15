import {Component, OnInit} from '@angular/core';
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {faCheck, faChevronRight, faPencilAlt, faTrashAlt, faXmark} from '@fortawesome/free-solid-svg-icons';
import {Employee} from '../entities/employee';
import {EmployeeView} from '../views/employee-view.model';
import {EmployeeMapperService} from '../services/employee-mapper.service';
import {EditEmployeeModal} from './edit-employee-modal/edit-employee-modal';

@Component({
  selector: 'app-employee-component',
  imports: [
    FaIconComponent,
    EditEmployeeModal
  ],
  templateUrl: './employee-component.html',
  styleUrl: './employee-component.scss'
})
export class EmployeeComponent implements OnInit {

  employees: Employee[] = [{
    id: 1,
    firstName: "123",
    lastName: "123",
    gender: "123",
    employmentDate: "123",
    office: {
      id: 123,
      officeName: "123"
    },
    position: {
      id: 123,
      positionName: "123"
    },
    salary: {
      id: 123,
      amount: 123
    },
    address: {
      id: 123,
      street: "123",
      city: "123",
      buildingNumber: "123",
      postalCode: "123"
    }
  }];
  labels: EmployeeView[];
  confirmDelete: boolean[] = [];
  employeeView: EmployeeView[][] = [];
  showModal = false;
  chosenEmployee!: Employee;

  constructor(private mapper: EmployeeMapperService) {
    this.confirmDelete = this.employees.map(() => false);
    this.labels = this.mapper.mapToView({
      accountNumber: '',
      address: {
        id: 0,
        street: "",
        city: "",
        buildingNumber: "",
        postalCode: ""
      },
      employmentDate: '',
      firstName: '',
      gender: '',
      id: 0,
      lastName: '',
      office: {
        id: 0,
        officeName: ""
      },
      personalIdentificationNumber: '',
      position: {
        id: 0,
        positionName: ""
      },
      salary: {
        id: 0,
        amount: 0
      }
    })
  }

  openModal(employee: Employee): void {
    this.chosenEmployee = employee;
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
  }

  askDelete(index: number) {
    this.confirmDelete[index] = true;
  }

  cancelDelete(index: number) {
    this.confirmDelete[index] = false;
  }

  removeOffice(index: number) {
    this.employees.splice(index, 1);
    this.employeeView.splice(index, 1);
    this.confirmDelete.splice(index, 1);
  }

  createOffice() {
    const newEmployee: Employee = {
      id: 0,
      firstName: "",
      lastName: "",
      gender: "",
      employmentDate: "",
      office: {
        id: 0,
        officeName: ""
      },
      position: {
        id: 0,
        positionName: ""
      },
      salary: {
        id: 0,
        amount: 0
      },
      address: {
        id: 0,
        street: "",
        city: "",
        buildingNumber: "",
        postalCode: ""
      }
    }
    this.openModal(newEmployee);
  }

  saveEmployee(employee: Employee) {
    const index = this.employees.findIndex(e => e.id === employee.id);
    if (index === -1) {
      this.employees.push(employee);
      this.employeeView.push(this.mapper.mapToView(employee));
      this.confirmDelete.push(false);
    } else {
      this.employees[index] = employee;
      this.employeeView[index] = this.mapper.mapToView(employee);
    }
  }

  ngOnInit() {
    for (let office of this.employees) {
      this.employeeView.push(this.mapper.mapToView(office));
    }
  }

  protected readonly faChevronRight = faChevronRight;
  protected readonly faXmark = faXmark;
  protected readonly faCheck = faCheck;
  protected readonly faTrashAlt = faTrashAlt;
  protected readonly faPencilAlt = faPencilAlt;
}
