import {Component, OnInit} from '@angular/core';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {faCheck, faChevronRight, faPencilAlt, faTrashAlt, faXmark} from '@fortawesome/free-solid-svg-icons';
import {Office} from '../entities/office';
import {OfficeMapperService} from '../services/office-mapper.service';
import {OfficeView} from '../views/office-view.model';
import {EditOfficeModal} from './edit-office-modal/edit-office-modal';

@Component({
  selector: 'app-office-component',
  imports: [FontAwesomeModule, EditOfficeModal],
  templateUrl: './office-component.html',
  styleUrl: './office-component.scss'
})
export class OfficeComponent implements OnInit {
  faPencilAlt = faPencilAlt;
  faTrashAlt = faTrashAlt;
  faChevronRight = faChevronRight;

  offices: Office[] = [
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
    }, {
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
  ]

  labels: OfficeView[];
  confirmDelete: boolean[] = [];
  officesView: OfficeView[][] = [];
  showModal = false;
  chosenOffice!: Office;
  protected readonly faXmark = faXmark;
  protected readonly faCheck = faCheck;

  constructor(private mapper: OfficeMapperService) {
    this.confirmDelete = this.offices.map(() => false);
    this.labels = this.mapper.mapToView({address: undefined, id: 0, officeName: ''})
  }

  openModal(office: Office): void {
    this.chosenOffice = office;
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
  }

  askDelete(index: number) {
    this.confirmDelete[index] = true;
  }

  cancelDelete(index: number) {
    this.confirmDelete[index] = false;
  }

  removeOffice(index: number) {
    this.offices.splice(index, 1);
    this.officesView.splice(index, 1);
    this.confirmDelete.splice(index, 1);
  }

  createOffice() {
    const newOffice: Office = {id: 0, officeName: ''}
    this.openModal(newOffice);
  }

  saveOffice(office: Office) {
    const index = this.offices.findIndex(o => o.id === office.id);
    if (index === -1) {
      this.offices.push(office);
      this.officesView.push(this.mapper.mapToView(office));
      this.confirmDelete.push(false);
    } else {
      this.offices[index] = office;
      this.officesView[index] = this.mapper.mapToView(office);
    }
  }

  ngOnInit() {
    for (let office of this.offices) {
      this.officesView.push(this.mapper.mapToView(office));
    }
  }
}
