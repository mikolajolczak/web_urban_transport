import { Injectable } from '@angular/core';
import { GenericCrudService } from './generic-crud.service';
import {Office} from '../entities/office';

@Injectable({
  providedIn: 'root'
})
export class OfficeService extends GenericCrudService<Office> {
  protected endpoint = 'office';
}
