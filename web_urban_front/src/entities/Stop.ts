import {Address} from './address';
import {CrudItem} from '../directives/base-crud.component';

export interface Stop extends CrudItem{
  id:number;
  stopName:string;
  address?: Address;
}
