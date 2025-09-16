import {Injectable} from '@angular/core';
import {TableViewModel} from '../views/table-view.model';
import {Office} from '../entities/office';
import {Address} from '../entities/address';

@Injectable({
  providedIn: 'root'
})
export class OfficeMapperService {

  mapToView(office: Office): TableViewModel[] {
    return [
      {label: "Name", value: office.officeName},
      {label: "Address", value: office.address ? this.formatAddress(office.address) : '-'},
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
