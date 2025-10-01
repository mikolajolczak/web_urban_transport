import {CrudItem} from '../directives/base-crud.component';

export interface Address extends CrudItem{
  id: number;
  street: string;
  city: string;
  buildingNumber: string;
  apartmentNumber?: string;
  postalCode: string;
}
