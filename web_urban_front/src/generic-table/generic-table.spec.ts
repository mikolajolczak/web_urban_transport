import {ComponentFixture, TestBed} from '@angular/core/testing';
import {GenericTable, TableColumn, TableItem} from './generic-table';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {CommonModule} from '@angular/common';
import {By} from '@angular/platform-browser';
import {Component} from '@angular/core';

interface TestItem extends TableItem {
  name: string;
  age: number;
}

@Component({
  imports: [
    GenericTable
  ],
  template: `
    <app-generic-table
      [title]="title"
      [createButtonText]="createButtonText"
      [items]="items"
      [columns]="columns"
      [tableWidth]="tableWidth"
      [showModal]="showModal"
      (create)="onCreate()"
      (edit)="onEdit($event)"
      (delete)="onDelete($event)"
      (closeModal)="onCloseModal()">
    </app-generic-table>
  `
})
class TestHostComponent {
  title = 'Test Table';
  createButtonText = 'Add Item';
  items: TestItem[] = [{id: 1, name: 'Alice', age: 30}];
  columns: TableColumn[] = [{label: 'Name', value: 'name'}, {label: 'Age', value: 'age'}];
  tableWidth = '80vw';
  showModal = false;
  createCalls = 0;
  editCalls: TestItem[] = [];
  deleteCalls: number[] = [];
  closeModalCalls = 0;

  onCreate() {
    this.createCalls++;
  }

  onEdit(item: TestItem) {
    this.editCalls.push(item);
  }

  onDelete(index: number) {
    this.deleteCalls.push(index);
  }

  onCloseModal() {
    this.closeModalCalls++;
  }
}

describe('GenericTable', () => {
  let fixture: ComponentFixture<TestHostComponent>;
  let component: TestHostComponent;
  let tableComponent: GenericTable<TestItem>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FontAwesomeModule, CommonModule, GenericTable, TestHostComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(TestHostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    tableComponent = fixture.debugElement.query(By.directive(GenericTable)).componentInstance;
  });

  it('should create the component', () => {
    expect(tableComponent).toBeTruthy();
  });

  it('should initialize confirmDelete array on init', () => {
    expect(tableComponent.confirmDelete.length).toBe(component.items.length);
    expect(tableComponent.confirmDelete[0]).toBeFalse();
  });

  it('should update confirmDelete array on input changes', () => {
    component.items.push({id: 2, name: 'Bob', age: 25});
    fixture.detectChanges();
    expect(tableComponent.confirmDelete.length).toBe(1);
    expect(tableComponent.confirmDelete[0]).toBeFalse();
  });

  it('should emit create event', () => {
    tableComponent.onCreate();
    expect(component.createCalls).toBe(1);
  });

  it('should emit edit event with correct item', () => {
    const item = component.items[0];
    tableComponent.onEdit(item);
    expect(component.editCalls.length).toBe(1);
    expect(component.editCalls[0]).toBe(item);
  });

  it('should emit closeModal event', () => {
    tableComponent.onCloseModal();
    expect(component.closeModalCalls).toBe(1);
  });

  it('should mark item for delete', () => {
    tableComponent.askDelete(0);
    expect(tableComponent.confirmDelete[0]).toBeTrue();
  });

  it('should cancel delete', () => {
    tableComponent.askDelete(0);
    tableComponent.cancelDelete(0);
    expect(tableComponent.confirmDelete[0]).toBeFalse();
  });

  it('should remove item and emit delete event', () => {
    tableComponent.removeItem(0);
    expect(component.deleteCalls).toEqual([0]);
    expect(tableComponent.confirmDelete.length).toBe(0);
  });

  it('should get nested item values correctly', () => {
    const value = tableComponent.getItemValue(component.items[0], 'name');
    expect(value).toBe('Alice');
  });

  it('should return undefined for non-existing path', () => {
    const value = tableComponent.getItemValue(component.items[0], 'nonexistent');
    expect(value).toBeUndefined();
  });
});
