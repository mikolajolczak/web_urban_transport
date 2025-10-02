import {Office} from './office';
import {CrudItem} from '../directives/base-crud.component';

export interface Vehicle extends CrudItem {
  id: number;
  brand: string;
  productionYear?: number;
  model: string;
  registrationNumber: string;
  purchaseDate: string;
  insuranceDate: string;
  office: Office;

}
