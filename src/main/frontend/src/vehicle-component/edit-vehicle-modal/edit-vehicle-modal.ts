import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Vehicle} from '../../entities/vehicle';
import {faChevronRight, faFloppyDisk} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {FormsModule} from '@angular/forms';
import {Office} from '../../entities/office';

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

  offices: Office[] = [
    {
      id: 1, officeName: 'Warszawa',
      address: undefined
    },
    {id: 2, officeName: 'Kraków'},
    {id: 3, officeName: 'Gdańsk'},
  ];

  save() {
    this.saveVehicle.emit(this.vehicle);
    this.close.emit();
  }

  get purchaseDateString(): string {
    return this.vehicle.purchaseDate ?
      this.vehicle.purchaseDate.toISOString().split('T')[0] : '';
  }

  set purchaseDateString(value: string) {
    this.vehicle.purchaseDate = value ? new Date(value) : null;
  }

  get insuranceDateString(): string {
    return this.vehicle.insuranceDate ?
      this.vehicle.insuranceDate.toISOString().split('T')[0] : '';
  }

  set insuranceDateString(value: string) {
    this.vehicle.insuranceDate = value ? new Date(value) : null;
  }
}
