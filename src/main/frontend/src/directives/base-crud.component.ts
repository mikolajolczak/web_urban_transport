import { Directive, OnInit } from '@angular/core';

export interface CrudItem {
  id: number | string;
}

export interface CrudMapper<T, V> {
  mapToView(item: T): V[];
}

@Directive()
export abstract class BaseCrudComponent<T extends CrudItem, V> implements OnInit {
  items: T[] = [];
  itemsView: any[] = [];
  tableColumns: any[] = [];
  showModal = false;
  chosenItem!: T;

  protected constructor(protected mapper: CrudMapper<T, V>) {}

  ngOnInit() {
    this.initializeData();
    this.setupTableColumns();
    this.updateView();
  }

  protected abstract initializeData(): void;
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

  removeItem(index: number) {
    this.items.splice(index, 1);
    this.updateView();
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
