import { Injectable } from '@angular/core';
import { GenericCrudService } from './generic-crud.service';
import {Address} from '../entities/address';
import {Salary} from '../entities/salary';

@Injectable({
  providedIn: 'root'
})
export class SalaryService extends GenericCrudService<Salary> {
  protected endpoint = 'salary';
}
