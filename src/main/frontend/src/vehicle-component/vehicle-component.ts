import {Component, OnInit} from '@angular/core';
import {faCheck, faChevronRight, faPencilAlt, faTrashAlt, faXmark} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {Vehicle} from '../entities/vehicle';
import {VehicleMapperService} from '../services/vehicle-mapper.service';
import {VehicleView} from '../views/vehicle-view.model';
import {EditVehicleModal} from './edit-vehicle-modal/edit-vehicle-modal';

@Component({
  selector: 'app-vehicle-component',
  imports: [FontAwesomeModule, EditVehicleModal],
  templateUrl: './vehicle-component.html',
  styleUrl: './vehicle-component.scss'
})
export class VehicleComponent implements OnInit {
  faPencilAlt = faPencilAlt
  faTrashAlt = faTrashAlt
  faChevronRight = faChevronRight
  faCheck = faCheck;
  faXmark = faXmark;

  showModal = false;
  confirmDelete: boolean[] = [];
  chosenVehicle!: Vehicle;
  vehicles: Vehicle[] = [
    {
      vehicleId: 0,
      brand: "1",
      productionYear: 2,
      model: "3",
      registrationNumber: "3",
      purchaseDate: new Date(),
      insuranceDate: new Date(),
      office: {
        id: 1,
        officeName: "1",
        address: {
          id: 1,
          street: "1",
          city: "1",
          buildingNumber: "1",
          postalCode: "2"
        }
      }
    }, {
      vehicleId: 1,
      brand: "1",
      productionYear: 2,
      model: "3",
      registrationNumber: "3",
      purchaseDate: new Date(),
      insuranceDate: new Date(),
      office: {
        id: 1,
        officeName: "asdasasd",
        address: {
          id: 1,
          street: "1",
          city: "1",
          buildingNumber: "1",
          postalCode: "2"
        }
      }
    }
  ]
  labels: VehicleView[];
  vehiclesView: VehicleView[][] = [];

  constructor(private mapper: VehicleMapperService) {
    this.confirmDelete = this.vehicles.map(() => false);
    this.labels = this.mapper.mapToView({
      brand: '',
      insuranceDate: new Date,
      model: '',
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
      },
      productionYear: 0,
      purchaseDate: new Date,
      registrationNumber: '',
      vehicleId: 0
    })
  }

  openModal(vehicle: Vehicle) {
    this.showModal = true;
    this.chosenVehicle = vehicle;
  }

  closeModal() {
    this.showModal = false;
  }

  askDelete(index: number) {
    this.confirmDelete[index] = true;
  }

  cancelDelete(index: number) {
    this.confirmDelete[index] = false;
  }

  removeVehicle(index: number) {
    this.vehicles.splice(index, 1);
    this.vehiclesView.splice(index, 1);
    this.confirmDelete.splice(index, 1);
  }

  saveVehicle(vehicle: Vehicle) {
    const index = this.vehicles.findIndex(v => v.vehicleId === vehicle.vehicleId);
    if (index === -1) {
      this.vehicles.push(vehicle);
      this.vehiclesView.push(this.mapper.mapToView(vehicle));
      this.confirmDelete.push(false);
    } else {
      this.vehicles[index] = vehicle;
      this.vehiclesView[index] = this.mapper.mapToView(vehicle);
    }
  }

  createVehicle() {
    const vehicle: Vehicle = {
      vehicleId: this.vehicles.length + 1,
      brand: '',
      model: '',
      productionYear: null,
      registrationNumber: '',
      purchaseDate: null,
      insuranceDate: null,
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
    this.openModal(vehicle);
  }

  ngOnInit(): void {
    for (let vehicle of this.vehicles) {
      this.vehiclesView.push(this.mapper.mapToView(vehicle));
    }

  }

}
