import {Component, EventEmitter, Input, Output} from '@angular/core';
import {faChevronRight, faFloppyDisk} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {FormsModule} from '@angular/forms';
import {Stop} from '../../entities/Stop';

@Component({
  selector: 'app-edit-stop-modal',
  imports: [
    FontAwesomeModule, FormsModule
  ],
  templateUrl: './edit-stop-modal.html',
  styleUrl: './edit-stop-modal.scss'
})
export class EditStopModal {
  @Input() stop!: Stop;
  @Input() isEditMode!: boolean;
  @Output() closed = new EventEmitter<void>();
  @Output() createStop = new EventEmitter<Stop>();
  @Output() updateStop = new EventEmitter<Stop>();


  faChevronRight = faChevronRight;
  faFloppyDisk = faFloppyDisk;

  get city() {
    return this.stop.address?.city ?? '';
  }

  set city(value: string) {
    if (!this.stop.address) {
      this.stop.address = {apartmentNumber: '', buildingNumber: '', city: value, id: 0, postalCode: '', street: ''};
    }
    this.stop.address.city = value;
  }

  get street() {
    return this.stop.address?.street ?? '';
  }

  set street(value: string) {
    if (!this.stop.address) {
      this.stop.address = {apartmentNumber: '', buildingNumber: '', city: '', id: 0, postalCode: '', street: value};
    }
    this.stop.address.street = value;
  }

  get buildingNumber() {
    return this.stop.address?.buildingNumber ?? '';
  }

  set buildingNumber(value: string) {
    if (!this.stop.address) {
      this.stop.address = {apartmentNumber: '', buildingNumber: value, city: '', id: 0, postalCode: '', street: ''};
    }
    this.stop.address.buildingNumber = value;
  }

  get apartmentNumber() {
    return this.stop.address?.apartmentNumber ?? '';
  }

  set apartmentNumber(value: string) {
    if (!this.stop.address) {
      this.stop.address = {apartmentNumber: value, buildingNumber: '', city: '', id: 0, postalCode: '', street: ''};
    }
    this.stop.address.apartmentNumber = value;
  }

  get postalCode() {
    return this.stop.address?.postalCode ?? '';
  }

  set postalCode(value: string) {
    if (!this.stop.address) {
      this.stop.address = {apartmentNumber: '', buildingNumber: '', city: '', id: 0, postalCode: value, street: ''};
    }
    this.stop.address.postalCode = value;
  }

  save() {
    if (this.isEditMode) {
      this.updateStop.emit(this.stop);
    } else {
      this.createStop.emit(this.stop);
    }
    this.closed.emit();
  }
  get modalTitle(): string {
    return this.isEditMode ? 'Edit Stop' : 'Create New Stop';
  }

  get saveButtonText(): string {
    return this.isEditMode ? 'Update' : 'Create';
  }
}
