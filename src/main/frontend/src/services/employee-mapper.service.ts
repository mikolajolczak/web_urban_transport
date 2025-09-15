import {Injectable} from '@angular/core';
import {Employee} from '../entities/employee';
import {EmployeeView} from '../views/employee-view.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeMapperService {

  mapToView(employee: Employee): EmployeeView[] {
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
      {label: 'Personal Identification Number', value: employee.personalIdentificationNumber || '-'},
      {label: 'Employment Date', value: employee.employmentDate || '-'}
    ];
  }
}
