import {Component} from '@angular/core';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {Vehicle} from '../entities/vehicle';
import {VehicleMapperService} from '../services/vehicle-mapper.service';
import {TableViewModel} from '../views/table-view.model';
import {GenericTable} from '../generic-table/generic-table';
import {BaseCrudComponent, CRUD_MAPPER_TOKEN, CRUD_SERVICE_TOKEN} from '../directives/base-crud.component';
import {EditVehicleModal} from './edit-vehicle-modal/edit-vehicle-modal';
import {VehicleService} from '../services/vehicle.service';

@Component({
  selector: 'app-vehicle-component',
  imports: [FontAwesomeModule, GenericTable, EditVehicleModal],
  templateUrl: './vehicle-component.html',
  styleUrl: './vehicle-component.scss',
  providers: [{provide: CRUD_MAPPER_TOKEN, useClass: VehicleMapperService},
    {provide: CRUD_SERVICE_TOKEN, useClass: VehicleService}],
})
export class VehicleComponent extends BaseCrudComponent<Vehicle, TableViewModel, VehicleService> {

  protected setupTableColumns(): void {
    const sample = this.mapper.mapToView({
      id: 0,
      brand: '',
      productionYear: 0,
      model: '',
      registrationNumber: '',
      purchaseDate: '',
      insuranceDate: '',
      office: {
        id: 0,
        officeName: "",
        address: {
          id: 0,
          street: "",
          city: "",
          buildingNumber: "",
          postalCode: ""
        }
      }
    });

    this.tableColumns = sample.map(item => ({
      label: item.label,
      value: item.label.toLowerCase().replace(/\s+/g, '')
    }));
  }

  public createNewItem(): Vehicle {
    return {
      id: 0,
      brand: '',
      model: '',
      productionYear: new Date().getFullYear(),
      registrationNumber: '',
      purchaseDate: '',
      insuranceDate: '',
      office: {
        id: 0,
        officeName: '',
        address: {
          id: 0,
          street: '',
          city: '',
          buildingNumber: '',
          postalCode: ''
        }
      }
    };
  }

  public flattenItemForTable(vehicle: Vehicle): Record<string, string> {
    const mapped = this.mapper.mapToView(vehicle);
    const flattened: Record<string, string> = {};

    mapped.forEach(item => {
      const key = item.label.toLowerCase().replace(/\s+/g, '');
      flattened[key] = item.value;
    });

    return flattened;
  }

}
