import {Address} from './address';
import {Salary} from './salary';
import {Position} from './position';
import {Office} from './office';

export interface Employee {
  id: number;
  firstName: string;
  lastName: string;
  gender: string;
  accountNumber?: string;
  personalIdentificationNumber?: string;
  employmentDate: string;
  office: Office;
  position: Position;
  salary: Salary;
  address: Address;
}
