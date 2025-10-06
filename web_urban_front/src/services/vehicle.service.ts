import {Injectable} from '@angular/core';
import {GenericCrudService} from './generic-crud.service';
import {Vehicle} from '../entities/vehicle';

@Injectable({
  providedIn: 'root'
})
export class VehicleService extends GenericCrudService<Vehicle> {
  protected endpoint = 'vehicle';
}
