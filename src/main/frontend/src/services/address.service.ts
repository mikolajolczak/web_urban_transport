import { Injectable } from '@angular/core';
import { GenericCrudService } from './generic-crud.service';
import {Address} from '../entities/address';

@Injectable({
  providedIn: 'root'
})
export class AddressService extends GenericCrudService<Address> {
  protected endpoint = 'address';
}
