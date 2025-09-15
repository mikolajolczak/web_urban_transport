import {Component, EventEmitter, Input, Output} from '@angular/core';
import {faChevronRight, faFloppyDisk} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {FormsModule} from '@angular/forms';
import {Office} from '../../entities/office';

@Component({
  selector: 'app-edit-office-modal',
  imports: [
    FontAwesomeModule, FormsModule
  ],
  templateUrl: './edit-office-modal.html',
  styleUrl: './edit-office-modal.scss'
})
export class EditOfficeModal {
  @Input() office!: Office;
  @Output() close = new EventEmitter<void>();
  @Output() saveOffice = new EventEmitter<Office>();


  faChevronRight = faChevronRight;
  faFloppyDisk = faFloppyDisk;

  get city() {
    return this.office.address?.city ?? '';
  }

  set city(value: string) {
    if (!this.office.address) {
      this.office.address = {apartmentNumber: '', buildingNumber: '', city: value, id: 0, postalCode: '', street: ''};
    }
    this.office.address.city = value;
  }

  get street() {
    return this.office.address?.street ?? '';
  }

  set street(value: string) {
    if (!this.office.address) {
      this.office.address = {apartmentNumber: '', buildingNumber: '', city: '', id: 0, postalCode: '', street: value};
    }
    this.office.address.street = value;
  }

  get buildingNumber() {
    return this.office.address?.buildingNumber ?? '';
  }

  set buildingNumber(value: string) {
    if (!this.office.address) {
      this.office.address = {apartmentNumber: '', buildingNumber: value, city: '', id: 0, postalCode: '', street: ''};
    }
    this.office.address.buildingNumber = value;
  }

  get apartmentNumber() {
    return this.office.address?.apartmentNumber ?? '';
  }

  set apartmentNumber(value: string) {
    if (!this.office.address) {
      this.office.address = {apartmentNumber: value, buildingNumber: '', city: '', id: 0, postalCode: '', street: ''};
    }
    this.office.address.apartmentNumber = value;
  }

  get postalCode() {
    return this.office.address?.postalCode ?? '';
  }

  set postalCode(value: string) {
    if (!this.office.address) {
      this.office.address = {apartmentNumber: '', buildingNumber: '', city: '', id: 0, postalCode: value, street: ''};
    }
    this.office.address.postalCode = value;
  }

  save() {
    this.saveOffice.emit(this.office);
    this.close.emit();
  }


}
