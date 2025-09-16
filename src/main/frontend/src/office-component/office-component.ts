import {Component} from '@angular/core';
import {Office} from '../entities/office';
import {OfficeMapperService} from '../services/office-mapper.service';
import {TableViewModel} from '../views/table-view.model';
import {EditOfficeModal} from './edit-office-modal/edit-office-modal';
import {GenericTable} from '../generic-table/generic-table';
import {BaseCrudComponent} from '../directives/base-crud.component';

@Component({
  selector: 'app-office-component',
  standalone: true,
  imports: [GenericTable, EditOfficeModal],
  templateUrl: './office-component.html',
  styleUrls: ['./office-component.scss'],
})
export class OfficeComponent extends BaseCrudComponent<Office, TableViewModel> {

  constructor(mapper: OfficeMapperService) {
    super(mapper);
  }

  protected initializeData(): void {
    this.items = [
      {
        id: 1,
        officeName: "123",
        address: {
          id: 0,
          street: "123",
          city: "132",
          buildingNumber: "123",
          postalCode: "123"
        }
      },
      {
        id: 2,
        officeName: "asd",
        address: {
          id: 2,
          street: "asd",
          city: "ads",
          buildingNumber: "asd",
          postalCode: "asd"
        }
      }
    ];
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
