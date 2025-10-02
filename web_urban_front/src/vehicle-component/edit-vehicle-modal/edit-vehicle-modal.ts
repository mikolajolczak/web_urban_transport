import {Component, EventEmitter, inject, Input, Output} from '@angular/core';
import {Vehicle} from '../../entities/vehicle';
import {faChevronRight, faFloppyDisk} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {FormsModule} from '@angular/forms';
import {Office} from '../../entities/office';
import {OfficeService} from '../../services/office.service';

@Component({
  selector: 'app-edit-vehicle-modal',
  templateUrl: './edit-vehicle-modal.html',
  imports: [
    FormsModule, FontAwesomeModule
  ],
  styleUrls: ['./edit-vehicle-modal.scss']
})
export class EditVehicleModal {
  private _vehicle!: Vehicle;
  @Input()
  set vehicle(value: Vehicle) {
    this._vehicle = value;
    if (value) {
      this.loadOffices();
    }
  }

  get vehicle(): Vehicle {
    return this._vehicle;
  }

  @Input() isEditMode!: boolean;
  @Output() closed = new EventEmitter<void>();
  @Output() createVehicle = new EventEmitter<Vehicle>();
  @Output() updateVehicle = new EventEmitter<Vehicle>();

  faChevronRight = faChevronRight;
  faFloppyDisk = faFloppyDisk;

  private officeService = inject(OfficeService);

  offices: Office[] = [];

  loadOffices() {
    this.officeService.getAll().subscribe({
      next: offices => {
        this.offices = offices
      },
      error: err => console.error(err)
    });
  }

  save() {
    if (this.isEditMode) {
      this.updateVehicle.emit(this.vehicle);
    } else {
      this.createVehicle.emit(this.vehicle);
    }
    this.closed.emit();
  }

  get modalTitle(): string {
    return this.isEditMode ? 'Edit Vehicle' : 'Create New Vehicle';
  }

  get saveButtonText(): string {
    return this.isEditMode ? 'Update' : 'Create';
  }
}
