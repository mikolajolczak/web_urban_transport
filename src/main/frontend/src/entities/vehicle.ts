import {Office} from './office';

export interface Vehicle {
  id: number;
  brand: string;
  productionYear?: number;
  model: string;
  registrationNumber: string;
  purchaseDate: Date | null;
  insuranceDate: Date | null;
  office: Office;

}
