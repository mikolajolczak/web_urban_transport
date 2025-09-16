import {Component, OnInit} from '@angular/core';
import {Employee} from '../entities/employee';
import {TableViewModel} from '../views/table-view.model';
import {EmployeeMapperService} from '../services/employee-mapper.service';
import {faChevronRight, faUserEdit} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-about-component',
  templateUrl: './about-component.html',
  styleUrls: ['./about-component.scss'],
  imports: [FontAwesomeModule]
})
export class AboutComponent implements OnInit {

  employee: Employee = {
    id: 0,
    accountNumber: '123123123123213312',
    address: {
      apartmentNumber: "1",
      buildingNumber: "2",
      city: "3",
      postalCode: "4",
      street: "5",
      id: 0
    },
    employmentDate: '',
    firstName: '',
    gender: '',
    lastName: '',
    office: {
      address: {
        apartmentNumber: "1",
        buildingNumber: "2",
        city: "3",
        postalCode: "4",
        street: "5",
        id: 0
      },
      officeName: '6',
      id: 0
    },
    personalIdentificationNumber: '',
    position: {
      positionName: "7",
      id: 0
    },
    salary: {
      amount: 8,
      id: 0
    }
  };

  employeeView: TableViewModel[] = [];
  faChevronRight = faChevronRight;
  faUserEdit = faUserEdit;

  constructor(private mapper: EmployeeMapperService) {
  }

  ngOnInit(): void {
    this.employeeView = this.mapper.mapToView(this.employee);
  }

}
