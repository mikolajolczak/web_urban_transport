import {Component} from '@angular/core';
import {Office} from '../entities/office';
import {OfficeMapperService} from '../services/office-mapper.service';
import {TableViewModel} from '../views/table-view.model';
import {EditOfficeModal} from './edit-office-modal/edit-office-modal';
import {GenericTable} from '../generic-table/generic-table';
import {BaseCrudComponent} from '../directives/base-crud.component';
import {OfficeService} from '../services/office.service';

@Component({
  selector: 'app-office-component',
  standalone: true,
  imports: [GenericTable, EditOfficeModal],
  templateUrl: './office-component.html',
  styleUrls: ['./office-component.scss'],
})
export class OfficeComponent extends BaseCrudComponent<Office, TableViewModel, OfficeService> {

  constructor(mapper: OfficeMapperService, officeService: OfficeService) {
    super(mapper, officeService);
  }

  protected setupTableColumns(): void {
    const sample = this.mapper.mapToView({
      address: undefined,
      id: 0,
      officeName: ''
    });

    this.tableColumns = sample.map(item => ({
      label: item.label,
      value: item.label.toLowerCase().replace(/\s+/g, '')
    }));
  }

  protected createNewItem(): Office {
    return {
      id: 0,
      officeName: '',
      address: {
        id: 0,
        street: '',
        city: '',
        buildingNumber: '',
        postalCode: ''
      }
    };
  }

  protected flattenItemForTable(office: Office): any {
    const mapped = this.mapper.mapToView(office);
    const flattened: any = {};

    mapped.forEach(item => {
      const key = item.label.toLowerCase().replace(/\s+/g, '');
      flattened[key] = item.value;
    });

    return flattened;
  }
}
