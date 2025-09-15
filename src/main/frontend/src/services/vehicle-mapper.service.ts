import { Injectable } from '@angular/core';
import { Vehicle } from '../entities/vehicle';
import { VehicleView } from '../views/vehicle-view.model';

@Injectable({
  providedIn: 'root'
})
export class VehicleMapperService {

  private formatDate(date: Date | null | undefined): string {
    if (!date) return '-';
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${day}.${month}.${year}`;
  }

  mapToView(vehicle: Vehicle): VehicleView[] {
    return [
      { label: 'Brand', value: vehicle.brand || '-' },
      { label: 'Model', value: vehicle.model || '-' },
      { label: 'Production Year', value: vehicle.productionYear?.toString() || '-' },
      { label: 'Registration Number', value: vehicle.registrationNumber || '-' },
      { label: 'Purchase Date', value: this.formatDate(vehicle.purchaseDate) },
      { label: 'Insurance Date', value: this.formatDate(vehicle.insuranceDate) },
      { label: 'Office', value: vehicle.office?.officeName || '-' },
    ];
  }
}
