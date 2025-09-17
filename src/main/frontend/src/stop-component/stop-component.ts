import {Component} from '@angular/core';
import {BaseCrudComponent} from '../directives/base-crud.component';
import {Stop} from '../entities/Stop';
import {TableViewModel} from '../views/table-view.model';
import {StopMapperService} from '../services/stop-mapper.service';
import {GenericTable} from '../generic-table/generic-table';
import {EditStopModal} from './edit-stop-modal/edit-stop-modal';
import {StopService} from '../services/stop.service';

@Component({
  selector: 'app-stop-component',
  imports: [
    GenericTable,
    EditStopModal
  ],
  templateUrl: './stop-component.html',
  styleUrl: './stop-component.scss'
})
export class StopComponent extends BaseCrudComponent<Stop, TableViewModel, StopService> {
  constructor(mapper: StopMapperService, stopService: StopService) {
    super(mapper,stopService);
  }

  protected override setupTableColumns(): void {
    const sample = this.mapper.mapToView({address: undefined, id: 0, stopName: ''});

    this.tableColumns = sample.map(item => ({
      label: item.label,
      value: item.label.toLowerCase().replace(/\s+/g, '')
    }));
  }

  protected override createNewItem(): Stop {
    return {
      id: 0,
      stopName: "", address: {
        id: 0,
        street: "",
        city: "",
        buildingNumber: "",
        postalCode: ""
      }
    }
  }

  protected override flattenItemForTable(stop: Stop) {
    const mapped = this.mapper.mapToView(stop);
    const flattened: any = {};

    mapped.forEach(item => {
      const key = item.label.toLowerCase().replace(/\s+/g, '');
      flattened[key] = item.value;
    });

    return flattened;
  }
}
