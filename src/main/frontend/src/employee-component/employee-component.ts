import {Component} from '@angular/core';
import {Employee} from '../entities/employee';
import {TableViewModel} from '../views/table-view.model';
import {EditEmployeeModal} from './edit-employee-modal/edit-employee-modal';
import {BaseCrudComponent, CRUD_MAPPER_TOKEN, CRUD_SERVICE_TOKEN} from '../directives/base-crud.component';
import {EmployeeMapperService} from '../services/employee-mapper.service';
import {GenericTable} from '../generic-table/generic-table';
import {EmployeeService} from '../services/employee.service';

@Component({
  selector: 'app-employee-component',
  imports: [
    EditEmployeeModal,
    GenericTable,
  ],
  templateUrl: './employee-component.html',
  styleUrl: './employee-component.scss', providers: [{provide: CRUD_MAPPER_TOKEN, useClass: EmployeeMapperService},
    {provide: CRUD_SERVICE_TOKEN, useClass: EmployeeService}],
})
export class EmployeeComponent extends BaseCrudComponent<Employee, TableViewModel, EmployeeService> {
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

  protected flattenItemForTable(employee: Employee): Record<string, string> {
    const mapped = this.mapper.mapToView(employee);
    const flattened: Record<string, string> = {};

    mapped.forEach(item => {
      const key = item.label.toLowerCase().replace(/\s+/g, '');
      flattened[key] = item.value;
    });

    return flattened;
  }
}
