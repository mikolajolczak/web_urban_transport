import { TestBed } from '@angular/core/testing';
import { VehicleComponent } from './vehicle-component';
import { VehicleService } from '../services/vehicle.service';
import { VehicleMapperService } from '../services/vehicle-mapper.service';
import { CRUD_MAPPER_TOKEN, CRUD_SERVICE_TOKEN } from '../directives/base-crud.component';
import { ChangeDetectorRef } from '@angular/core';

describe('VehicleComponent', () => {
  let component: VehicleComponent;
  let vehicleServiceSpy: jasmine.SpyObj<VehicleService>;
  let vehicleMapperSpy: jasmine.SpyObj<VehicleMapperService>;

  beforeEach(() => {
    const serviceMock = jasmine.createSpyObj('VehicleService', ['getAll', 'create', 'update', 'delete']);
    const mapperMock = jasmine.createSpyObj('VehicleMapperService', ['mapToView']);

    TestBed.configureTestingModule({
      providers: [
        VehicleComponent,
        { provide: CRUD_SERVICE_TOKEN, useValue: serviceMock },
        { provide: CRUD_MAPPER_TOKEN, useValue: mapperMock },
        { provide: ChangeDetectorRef, useValue: { detectChanges: () => {} } }
      ]
    });

    component = TestBed.inject(VehicleComponent);
    vehicleServiceSpy = TestBed.inject(CRUD_SERVICE_TOKEN) as jasmine.SpyObj<VehicleService>;
    vehicleMapperSpy = TestBed.inject(CRUD_MAPPER_TOKEN) as jasmine.SpyObj<VehicleMapperService>;
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  describe('setupTableColumns', () => {
    it('should map vehicle to table columns', () => {
      const mappedView = [
        { label: 'Brand', value: 'Toyota' },
        { label: 'Model', value: 'Corolla' }
      ];
      vehicleMapperSpy.mapToView.and.returnValue(mappedView);

      component['setupTableColumns']();

      expect(component.tableColumns.length).toBe(2);
      expect(component.tableColumns[0]).toEqual({ label: 'Brand', value: 'brand' });
      expect(component.tableColumns[1]).toEqual({ label: 'Model', value: 'model' });
      expect(vehicleMapperSpy.mapToView).toHaveBeenCalled();
    });
  });

  describe('createNewItem', () => {
    it('should return a new Vehicle object with default values', () => {
      const newItem = component.createNewItem();

      expect(newItem).toEqual({
        id: 0,
        brand: '',
        model: '',
        productionYear: new Date().getFullYear(),
        registrationNumber: '',
        purchaseDate: '',
        insuranceDate: '',
        office: {
          id: 0,
          officeName: '',
          address: {
            id: 0,
            street: '',
            city: '',
            buildingNumber: '',
            postalCode: ''
          }
        }
      });
    });
  });

  describe('flattenItemForTable', () => {
    it('should flatten vehicle to key-value pairs even if address is undefined', () => {
      const vehicle = {
        id: 1,
        brand: 'Toyota',
        model: 'Corolla',
        productionYear: 2020,
        registrationNumber: 'ABC123',
        purchaseDate: '2020-01-01',
        insuranceDate: '2020-06-01',
        office: {
          id: 1,
          officeName: 'Main Office',
          address: undefined
        }
      };
      const mappedView = [
        { label: 'Brand', value: 'Toyota' },
        { label: 'Model', value: 'Corolla' },
        { label: 'Production Year', value: '2020' },
        { label: 'Registration Number', value: 'ABC123' }
      ];
      vehicleMapperSpy.mapToView.and.returnValue(mappedView);

      const flattened = component.flattenItemForTable(vehicle);

      expect(flattened).toEqual({
        brand: 'Toyota',
        model: 'Corolla',
        productionyear: '2020',
        registrationnumber: 'ABC123'
      });
      expect(vehicleMapperSpy.mapToView).toHaveBeenCalledWith(vehicle);
    });
  });
});
