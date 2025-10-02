import {Injectable} from '@angular/core';
import {Employee} from '../entities/employee';
import {TableViewModel} from '../views/table-view.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeMapperService {

  mapToView(employee: Employee): TableViewModel[] {
    return [
      {label: 'First Name', value: employee.firstName || '-'},
      {label: 'Last Name', value: employee.lastName || '-'},
      {label: 'Gender', value: employee.gender || '-'},
      {label: 'City', value: employee.address?.city || '-'},
      {label: 'Street', value: employee.address?.street || '-'},
      {label: 'Building Number', value: employee.address?.buildingNumber || '-'},
      {label: 'Apartment Number', value: employee.address?.apartmentNumber || '-'},
      {label: 'Postal Code', value: employee.address?.postalCode || '-'},
      {label: 'Office Name', value: employee.office?.officeName || '-'},
      {label: 'Position', value: employee.position?.positionName || '-'},
      {label: 'Salary', value: employee.salary?.amount?.toString() || '-'},
      {label: 'Account Number', value: employee.accountNumber || '-'},
      {label: 'Id Number', value: employee.personalIdentificationNumber || '-'},
      {label: 'Employ Date', value: employee.employmentDate || '-'}
    ];
  }
}
