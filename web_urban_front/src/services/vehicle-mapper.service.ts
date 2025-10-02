import {Injectable} from '@angular/core';
import {Vehicle} from '../entities/vehicle';
import {TableViewModel} from '../views/table-view.model';

@Injectable({
  providedIn: 'root'
})
export class VehicleMapperService {

  mapToView(vehicle: Vehicle): TableViewModel[] {
    return [
      {label: 'Brand', value: vehicle.brand || '-'},
      {label: 'Model', value: vehicle.model || '-'},
      {label: 'Production Year', value: vehicle.productionYear?.toString() || '-'},
      {label: 'Registration Number', value: vehicle.registrationNumber || '-'},
      {label: 'Purchase Date', value: vehicle.purchaseDate},
      {label: 'Insurance Date', value: vehicle.insuranceDate},
      {label: 'Office', value: vehicle.office?.officeName || '-'},
    ];
  }

}
