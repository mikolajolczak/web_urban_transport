import { TestBed, ComponentFixture } from '@angular/core/testing';
import { EmployeeComponent } from './employee-component';
import { EmployeeService } from '../services/employee.service';
import { EmployeeMapperService } from '../services/employee-mapper.service';
import {CRUD_SERVICE_TOKEN, CRUD_MAPPER_TOKEN, CrudMapper} from '../directives/base-crud.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('EmployeeComponent', () => {
  let component: EmployeeComponent;
  let fixture: ComponentFixture<EmployeeComponent>;
  let mapper: CrudMapper<unknown, unknown>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmployeeComponent, HttpClientTestingModule],
      providers: [
        { provide: CRUD_SERVICE_TOKEN, useClass: EmployeeService },
        { provide: CRUD_MAPPER_TOKEN, useClass: EmployeeMapperService }
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(EmployeeComponent);
    component = fixture.componentInstance;
    mapper = TestBed.inject(CRUD_MAPPER_TOKEN);
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should return a new empty Employee', () => {
    const employee = component.createNewItem();
    expect(employee).toEqual({
      id: 0,
      firstName: '',
      lastName: '',
      gender: '',
      employmentDate: '',
      office: { id: 0, officeName: '' },
      position: { id: 0, positionName: '' },
      salary: { id: 0, amount: 0 },
      address: { id: 0, street: '', city: '', buildingNumber: '', postalCode: '' },
    });
  });

  it('should flatten an employee for table', () => {
    const employee = component.createNewItem();
    const flattened = component.flattenItemForTable(employee);
    console.log(flattened);
    expect(flattened).toEqual({
      "firstname": "-",
      "lastname": "-",
      "gender": "-",
      "city": "-",
      "street": "-",
      "buildingnumber": "-",
      "apartmentnumber": "-",
      "postalcode": "-",
      "officename": "-",
      "position": "-",
      "salary": "0",
      "accountnumber": "-",
      "idnumber": "-",
      "employdate": "-"
    });
  });

  it('should setup table columns correctly', () => {
    spyOn(mapper, 'mapToView').and.returnValue([
      { label: 'firstName', value: 'firstname' },
      { label: 'lastName', value: 'lastname' },
    ]);
    component['setupTableColumns']();
    expect(component.tableColumns.slice(0,2)).toEqual([
      { label: 'First Name', value: 'firstname' },
      { label: 'Last Name', value: 'lastname' },
    ]);
  });
});
