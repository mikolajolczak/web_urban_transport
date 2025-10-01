import {CrudItem} from '../directives/base-crud.component';

export interface Position extends CrudItem {
  id: number;
  positionName: string;
}
