import {Office} from './office';

export interface Vehicle {
  id: number;
  brand: string;
  productionYear?: number;
  model: string;
  registrationNumber: string;
  purchaseDate: string;
  insuranceDate: string;
  office: Office;

}
