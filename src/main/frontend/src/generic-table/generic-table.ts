import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FaIconComponent, FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {CommonModule} from '@angular/common';
import {faCheck, faChevronRight, faPencilAlt, faTrashAlt, faXmark} from '@fortawesome/free-solid-svg-icons';

export interface TableItem {
  id: number | string;
  [key: string]: any;
}

export interface TableColumn {
  label: string;
  value: string;
}
@Component({
  selector: 'app-generic-table',
  standalone: true,
    imports: [
        FaIconComponent, FontAwesomeModule, CommonModule
    ],
  templateUrl: './generic-table.html',
  styleUrl: './generic-table.scss'
})
export class GenericTable <T extends TableItem> {
  @Input() title: string = '';
  @Input() createButtonText: string = 'New Item';
  @Input() items: T[] = [];
  @Input() columns: TableColumn[] = [];
  @Input() tableWidth: string = '60vw';
  @Input() showModal: boolean = false;

  @Output() create = new EventEmitter<void>();
  @Output() edit = new EventEmitter<T>();
  @Output() delete = new EventEmitter<number>();
  @Output() closeModal = new EventEmitter<void>();

  faPencilAlt = faPencilAlt;
  faTrashAlt = faTrashAlt;
  faChevronRight = faChevronRight;
  faXmark = faXmark;
  faCheck = faCheck;

  confirmDelete: boolean[] = [];

  ngOnInit() {
    this.updateConfirmDelete();
  }

  ngOnChanges() {
    this.updateConfirmDelete();
  }

  private updateConfirmDelete() {
    this.confirmDelete = this.items.map((_, index) =>
      this.confirmDelete[index] || false
    );
  }

  onCreate() {
    this.create.emit();
  }

  onEdit(item: T) {
    this.edit.emit(item);
  }

  onCloseModal() {
    this.closeModal.emit();
  }

  askDelete(index: number) {
    this.confirmDelete[index] = true;
  }

  cancelDelete(index: number) {
    this.confirmDelete[index] = false;
  }

  removeItem(index: number) {
    this.confirmDelete.splice(index, 1);
    this.delete.emit(index);
  }

  getItemValue(item: T, path: string): any {
    return path.split('.').reduce((obj, key) => obj?.[key], item);
  }
}
