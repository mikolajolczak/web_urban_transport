import {Injectable} from '@angular/core';
import {GenericCrudService} from './generic-crud.service';
import {Position} from '../entities/position';

@Injectable({
  providedIn: 'root'
})
export class PositionService extends GenericCrudService<Position> {
  protected endpoint = 'position';
}
