import {Injectable} from '@angular/core';
import {Vehicle} from '../entities/vehicle';
import {VehicleView} from '../views/vehicle-view.model';

@Injectable({
  providedIn: 'root'
})
export class VehicleMapperService {

  mapToView(vehicle: Vehicle): VehicleView[] {
    return [
      {label: 'Brand', value: vehicle.brand || '-'},
      {label: 'Model', value: vehicle.model || '-'},
      {label: 'Production Year', value: vehicle.productionYear.toString() || '-'},
      {label: 'Registration Number', value: vehicle.registrationNumber || '-'},
      {label: 'Purchase Date', value: vehicle.purchaseDate.getFullYear().toString() || '-'},
      {label: 'Insurance Date', value: vehicle.insuranceDate.getFullYear().toString() || '-'},
      {label: 'Office', value: vehicle.office.officeName || '-'},
    ];
  }
}
