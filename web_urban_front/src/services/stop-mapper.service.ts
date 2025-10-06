import {Injectable} from '@angular/core';
import {TableViewModel} from '../views/table-view.model';
import {Address} from '../entities/address';
import {Stop} from '../entities/Stop';

@Injectable({
  providedIn: 'root'
})
export class StopMapperService {

  mapToView(stop: Stop): TableViewModel[] {
    return [
      {label: "Name", value: stop.stopName},
      {label: "Address", value: stop.address ? this.formatAddress(stop.address) : '-'},
    ];
  }

  private formatAddress(address: Address): string {
    let addr = `${address.street} ${address.buildingNumber}`;
    if (address.apartmentNumber) {
      addr += `/${address.apartmentNumber}`;
    }
    addr += `, ${address.city}, ${address.postalCode}`;
    return addr;
  }
}
