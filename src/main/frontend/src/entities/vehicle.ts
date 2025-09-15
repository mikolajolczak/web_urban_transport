import {Office} from './office';

export interface Vehicle {
  vehicleId: number;
  brand: string;
  productionYear: number;
  model: string;
  registrationNumber: string;
  purchaseDate: Date;
  insuranceDate: Date;
  office: Office;

}
