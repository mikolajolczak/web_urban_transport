import {Component} from '@angular/core';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {Vehicle} from '../entities/vehicle';
import {VehicleMapperService} from '../services/vehicle-mapper.service';
import {TableViewModel} from '../views/table-view.model';
import {GenericTable} from '../generic-table/generic-table';
import {BaseCrudComponent} from '../directives/base-crud.component';
import {EditVehicleModal} from './edit-vehicle-modal/edit-vehicle-modal';
import {VehicleService} from '../services/vehicle.service';

@Component({
  selector: 'app-vehicle-component',
  standalone: true,
  imports: [FontAwesomeModule, GenericTable, EditVehicleModal],
  templateUrl: './vehicle-component.html',
  styleUrl: './vehicle-component.scss'
})
export class VehicleComponent extends BaseCrudComponent<Vehicle, TableViewModel, VehicleService> {

  constructor(mapper: VehicleMapperService, vehicleService: VehicleService) {
    super(mapper,vehicleService);
  }

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

  protected createNewItem(): Vehicle {
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

  protected flattenItemForTable(vehicle: Vehicle): any {
    const mapped = this.mapper.mapToView(vehicle);
    const flattened: any = {};

    mapped.forEach(item => {
      const key = item.label.toLowerCase().replace(/\s+/g, '');
      flattened[key] = item.value;
    });

    return flattened;
  }

}
