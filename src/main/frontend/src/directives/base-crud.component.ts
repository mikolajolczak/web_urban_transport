import {ChangeDetectorRef, Directive, inject, OnInit} from '@angular/core';
import {GenericCrudService} from '../services/generic-crud.service';

export interface CrudItem {
  id: number;
}

export interface CrudMapper<T, V> {
  mapToView(item: T): V[];
}

@Directive()
export abstract class BaseCrudComponent<T extends CrudItem, V, S extends GenericCrudService<T>> implements OnInit {
  items: T[] = [];
  itemsView: any[] = [];
  tableColumns: any[] = [];
  showModal = false;
  chosenItem!: T;
  cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  protected constructor(protected mapper: CrudMapper<T, V>, protected service: S) {
  }

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

  protected abstract flattenItemForTable(item: T): any;

  protected updateView() {
    this.itemsView = this.items.map(item => ({
      id: item.id,
      originalData: item,
      ...this.flattenItemForTable(item)
    }));
  }

  openModal(itemView: any): void {
    this.chosenItem = itemView.originalData;
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
  }

  createItem() {
    this.chosenItem = this.createNewItem();
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
        console.error(err);
      }
    });
  }

  saveItem(item: T) {
    const index = this.items.findIndex(i => i.id === item.id);

    if (index === -1) {
      const maxId = this.items.length > 0 ? Math.max(...this.items.map(i => Number(i.id))) : 0;
      item.id = maxId + 1;
      this.items.push(item);
    } else {
      this.items[index] = item;
    }

    this.updateView();
    this.closeModal();
  }
}
