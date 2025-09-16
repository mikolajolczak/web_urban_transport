import {Component} from '@angular/core';
import {BaseCrudComponent} from '../directives/base-crud.component';
import {Stop} from '../entities/Stop';
import {TableViewModel} from '../views/table-view.model';
import {StopMapperService} from '../services/stop-mapper.service';
import {GenericTable} from '../generic-table/generic-table';
import {EditStopModal} from './edit-stop-modal/edit-stop-modal';

@Component({
  selector: 'app-stop-component',
  imports: [
    GenericTable,
    EditStopModal
  ],
  templateUrl: './stop-component.html',
  styleUrl: './stop-component.scss'
})
export class StopComponent extends BaseCrudComponent<Stop, TableViewModel> {
  constructor(mapper: StopMapperService) {
    super(mapper);
  }

  protected override initializeData(): void {
    this.items = [{
      id: 1,
      stopName: "123"
    }, {
      id: 2,
      stopName: "asd", address: {
        id: 1,
        street: "asd",
        city: "asd",
        buildingNumber: "asd",
        postalCode: "ads"
      }
    }]
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
