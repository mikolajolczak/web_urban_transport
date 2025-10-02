import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {FaIconComponent, FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {CommonModule} from '@angular/common';
import {faCheck, faChevronRight, faPencilAlt, faTrashAlt, faXmark} from '@fortawesome/free-solid-svg-icons';

export interface TableItem {
  id: number | string;

  [key: string]: string | number;
}

export interface TableColumn {
  label: string;
  value: string;
}

@Component({
  selector: 'app-generic-table',
  imports: [
    FaIconComponent, FontAwesomeModule, CommonModule
  ],
  templateUrl: './generic-table.html',
  styleUrl: './generic-table.scss'
})
export class GenericTable<T extends TableItem> implements OnInit, OnChanges {
  @Input() title = '';
  @Input() createButtonText = 'New Item';
  @Input() items: T[] = [];
  @Input() columns: TableColumn[] = [];
  @Input() tableWidth = '60vw';
  @Input() showModal = false;

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

  getItemValue(item: T, path: string): string | number | undefined {
    const keys = path.split('.');

    let result: string | number | TableItem | undefined = item;

    for (const key of keys) {
      if (typeof result === 'object' && result !== null && key in result) {
        result = result[key] as string | number | TableItem;
      } else {
        return undefined;
      }
    }

    return typeof result === 'string' || typeof result === 'number' ? result : undefined;
  }
}
