import {ChangeDetectorRef, Directive, inject, InjectionToken, OnInit} from '@angular/core';
import {GenericCrudService} from '../services/generic-crud.service';
import {TableViewModel} from '../views/table-view.model';
import {TableItem} from '../generic-table/generic-table';

export interface CrudItem {
  id: number;
}

export interface CrudMapper<T, V> {
  mapToView(item: T): V[];
}
export const CRUD_MAPPER_TOKEN = new InjectionToken<CrudMapper<unknown, unknown>>(
  'CRUD_MAPPER_TOKEN'
);
export const CRUD_SERVICE_TOKEN = new InjectionToken<GenericCrudService<unknown>>('CRUD_SERVICE_TOKEN');
@Directive()
export abstract class BaseCrudComponent<T extends CrudItem, V, S extends GenericCrudService<T>> implements OnInit {
  items: T[] = [];
  itemsView: TableItem[] = [];
  tableColumns: TableViewModel[] = [];
  showModal = false;
  chosenItem!: T;
  isEditMode = false;
  cdr: ChangeDetectorRef = inject(ChangeDetectorRef);
  protected mapper = inject<CrudMapper<T, V>>(CRUD_MAPPER_TOKEN);
  protected service = inject<S>(CRUD_SERVICE_TOKEN as InjectionToken<S>);

  ngOnInit() {
    this.initializeData();
    this.setupTableColumns();
  }

  protected initializeData(): void {
    this.service.getAll().subscribe({
      next: data => {
        this.items = data;
        this.updateView();
        this.cdr.detectChanges();
      }
    })
  };

  protected abstract setupTableColumns(): void;

  protected abstract createNewItem(): T;

  protected abstract flattenItemForTable(item: T): Record<string, string>;

  protected updateView() {
    this.itemsView = this.items.map(item => ({
      id: item.id,
      ...this.flattenItemForTable(item)
    }));
  }

  openModal(itemView: TableItem): void {
    const originalItem = this.items.find(item => item.id === itemView.id);
    if (originalItem) {
      this.chosenItem = { ...originalItem };
      this.isEditMode = true;
      this.showModal = true;
    }
  }

  closeModal(): void {
    this.showModal = false;
    this.isEditMode = false;
  }

  createItem() {
    this.chosenItem = this.createNewItem();
    this.isEditMode = false;
    this.showModal = true;
  }

  removeItem(index: number): void {
    const item = this.items[index];
    if (!item) return;
    this.service.delete(item.id).subscribe({
      next: () => {
        this.items.splice(index, 1);
        this.updateView();
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error( err);
      }
    });
  }

  saveItem(item: T) {
    this.service.create(item).subscribe({
      next: (createdItem) => {
        this.items.push(createdItem);
        this.updateView();
        this.closeModal();
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error( err);
      }
    });
  }

  updateItem(item: T) {
    this.service.update(item.id, item).subscribe({
      next: (updatedItem) => {
        const index = this.items.findIndex(i => i.id === item.id);
        if (index !== -1) {
          this.items[index] = updatedItem;
          this.updateView();
        }
        this.closeModal();
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error(err);
      }
    });
  }
}
