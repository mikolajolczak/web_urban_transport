import {ComponentFixture, TestBed} from '@angular/core/testing';
import {OfficeComponent} from './office-component';
import {OfficeService} from '../services/office.service';
import {OfficeMapperService} from '../services/office-mapper.service';
import {CRUD_MAPPER_TOKEN, CRUD_SERVICE_TOKEN, CrudMapper} from '../directives/base-crud.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('OfficeComponent', () => {
  let component: OfficeComponent;
  let fixture: ComponentFixture<OfficeComponent>;
  let mapper: CrudMapper<unknown, unknown>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OfficeComponent, HttpClientTestingModule],
      providers: [
        {provide: CRUD_SERVICE_TOKEN, useClass: OfficeService},
        {provide: CRUD_MAPPER_TOKEN, useClass: OfficeMapperService},

      ]
    }).compileComponents();

    fixture = TestBed.createComponent(OfficeComponent);
    component = fixture.componentInstance;
    mapper = TestBed.inject(CRUD_MAPPER_TOKEN);
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should return a new empty Office', () => {
    const office = component.createNewItem();
    expect(office).toEqual({
      id: 0,
      officeName: '',
      address: {id: 0, street: '', city: '', buildingNumber: '', postalCode: ''}
    });
  });

  it('should flatten an office for table', () => {
    const office = component.createNewItem();
    office.officeName = 'Main Office';
    office.address={apartmentNumber: '', buildingNumber: '123', city: 'Test', id: 1, postalCode: '123', street: 'Main'}
    const flattened = component.flattenItemForTable(office);
    expect(flattened).toEqual({name: 'Main Office', address: 'Main 123, Test, 123'});
  });

  it('should setup table columns correctly', () => {
    spyOn(mapper, 'mapToView').and.returnValue([
      {label: 'officeName', value: ''},
      {label: 'address', value: ''}
    ]);
    component['setupTableColumns']();
    expect(component.tableColumns).toEqual([
      {label: 'Name', value: 'name'},
      {label: 'Address', value: 'address'}
    ]);
  });
});
