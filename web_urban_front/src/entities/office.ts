import {Address} from './address';
import {CrudItem} from '../directives/base-crud.component';

export interface Office extends CrudItem {
  id: number;
  officeName: string;
  address?: Address;
}
