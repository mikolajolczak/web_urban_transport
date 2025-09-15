import {Office} from './office';

export interface Vehicle {
  vehicleId: number;
  brand: string;
  productionYear: number | null;
  model: string;
  registrationNumber: string;
  purchaseDate: Date | null;
  insuranceDate: Date | null;
  office: Office;

}
