import { Injectable } from '@angular/core';
import { GenericCrudService } from './generic-crud.service';
import {Stop} from '../entities/Stop';

@Injectable({
  providedIn: 'root'
})
export class StopService extends GenericCrudService<Stop> {
  protected endpoint = 'stop';
}
