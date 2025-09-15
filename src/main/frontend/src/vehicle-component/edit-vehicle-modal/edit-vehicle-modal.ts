import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Vehicle} from '../../entities/vehicle';
import {faChevronRight, faFloppyDisk} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-edit-vehicle-modal',
  templateUrl: './edit-vehicle-modal.html',
  imports: [
    FormsModule, FontAwesomeModule
  ],
  styleUrls: ['./edit-vehicle-modal.scss']
})
export class EditVehicleModal {
  @Input() vehicle!: Vehicle;
  @Output() close = new EventEmitter<void>();
  @Output() saveVehicle = new EventEmitter<Vehicle>();

  faChevronRight = faChevronRight;
  faFloppyDisk = faFloppyDisk;

  offices = [
    {id: 1, officeName: 'Warszawa'},
    {id: 2, officeName: 'Kraków'},
    {id: 3, officeName: 'Gdańsk'},
  ];

  save() {
    this.saveVehicle.emit(this.vehicle);
    this.close.emit();
  }

  get purchaseYear(): number {
    return this.vehicle.purchaseDate.getFullYear();
  }

  set purchaseYear(year: number) {
    if (this.vehicle.purchaseDate) {
      this.vehicle.purchaseDate.setFullYear(+year);
    } else {
      this.vehicle.purchaseDate = new Date(+year, 0, 1);
    }
  }

  get insuranceYear(): number {
    return this.vehicle.insuranceDate.getFullYear();
  }

  set insuranceYear(year: number) {
    if (this.vehicle.insuranceDate) {
      this.vehicle.insuranceDate.setFullYear(+year);
    } else {
      this.vehicle.insuranceDate = new Date(+year, 0, 1);
    }
  }
}
