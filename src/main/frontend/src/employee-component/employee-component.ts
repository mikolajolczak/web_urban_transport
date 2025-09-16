import {Component} from '@angular/core';
import {Employee} from '../entities/employee';
import {TableViewModel} from '../views/table-view.model';
import {EditEmployeeModal} from './edit-employee-modal/edit-employee-modal';
import {BaseCrudComponent} from '../directives/base-crud.component';
import {EmployeeMapperService} from '../services/employee-mapper.service';
import {GenericTable} from '../generic-table/generic-table';

@Component({
  selector: 'app-employee-component',
  imports: [
    EditEmployeeModal,
    GenericTable
  ],
  templateUrl: './employee-component.html',
  styleUrl: './employee-component.scss'
})
export class EmployeeComponent extends BaseCrudComponent<Employee, TableViewModel> {

  constructor(mapper: EmployeeMapperService) {
    super(mapper);
  }

  protected initializeData(): void {
    this.items = [
      {
        id: 1,
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
      },      {
        id: 2,
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
    ];
  }

  protected setupTableColumns(): void {
    const sample = this.mapper.mapToView({
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
    });

    this.tableColumns = sample.map(item => ({
      label: item.label,
      value: item.label.toLowerCase().replace(/\s+/g, '')
    }));
  }

  protected createNewItem(): Employee {
    return {
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
    };
  }

  protected flattenItemForTable(employee: Employee): any {
    const mapped = this.mapper.mapToView(employee);
    const flattened: any = {};

    mapped.forEach(item => {
      const key = item.label.toLowerCase().replace(/\s+/g, '');
      flattened[key] = item.value;
    });

    return flattened;
  }
}
