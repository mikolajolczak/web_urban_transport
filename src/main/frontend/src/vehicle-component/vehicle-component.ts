import {Component} from '@angular/core';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {Vehicle} from '../entities/vehicle';
import {VehicleMapperService} from '../services/vehicle-mapper.service';
import {TableViewModel} from '../views/table-view.model';
import {GenericTable} from '../generic-table/generic-table';
import {BaseCrudComponent} from '../directives/base-crud.component';
import {EditVehicleModal} from './edit-vehicle-modal/edit-vehicle-modal';

@Component({
  selector: 'app-vehicle-component',
  standalone: true,
  imports: [FontAwesomeModule, GenericTable, EditVehicleModal],
  templateUrl: './vehicle-component.html',
  styleUrl: './vehicle-component.scss'
})
export class VehicleComponent extends BaseCrudComponent<Vehicle, TableViewModel> {

  constructor(mapper: VehicleMapperService) {
    super(mapper);
  }

  protected initializeData(): void {
    this.items = [
      {
        id: 1,
        brand: "Toyota",
        productionYear: 2020,
        model: "Corolla",
        registrationNumber: "ABC123",
        purchaseDate: new Date('2020-01-15'),
        insuranceDate: new Date('2024-01-15'),
        office: {
          id: 1,
          officeName: "Main Office",
          address: {
            id: 1,
            street: "Main St",
            city: "Warsaw",
            buildingNumber: "123",
            postalCode: "00-001"
          }
        }
      },
      {
        id: 2,
        brand: "Honda",
        productionYear: 2019,
        model: "Civic",
        registrationNumber: "XYZ789",
        purchaseDate: new Date('2019-03-20'),
        insuranceDate: new Date('2024-03-20'),
        office: {
          id: 1,
          officeName: "Branch Office",
          address: {
            id: 1,
            street: "Second St",
            city: "Krakow",
            buildingNumber: "456",
            postalCode: "30-001"
          }
        }
      }
    ];
  }

  protected setupTableColumns(): void {
    const sample = this.mapper.mapToView({
      id: 0,
      brand: '',
      productionYear: 0,
      model: '',
      registrationNumber: '',
      purchaseDate: new Date(),
      insuranceDate: new Date(),
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
      purchaseDate: new Date(),
      insuranceDate: new Date(),
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
