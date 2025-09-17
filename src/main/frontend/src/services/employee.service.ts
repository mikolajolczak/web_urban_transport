import { Injectable } from '@angular/core';
import { Employee } from '../entities/employee';
import { GenericCrudService } from './generic-crud.service';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService extends GenericCrudService<Employee> {
  protected endpoint = 'employee';
}
