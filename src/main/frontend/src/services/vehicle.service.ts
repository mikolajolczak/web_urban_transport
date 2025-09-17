import { Injectable } from '@angular/core';
import { GenericCrudService } from './generic-crud.service';
import {Stop} from '../entities/Stop';
import {Vehicle} from '../entities/vehicle';

@Injectable({
  providedIn: 'root'
})
export class VehicleService extends GenericCrudService<Vehicle> {
  protected endpoint = 'vehicle';
}
