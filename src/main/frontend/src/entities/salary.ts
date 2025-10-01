import {CrudItem} from '../directives/base-crud.component';

export interface Salary extends CrudItem {
  id: number;
  amount: number;
}
