import {TestBed} from '@angular/core/testing';
import {StopComponent} from './stop-component';
import {StopService} from '../services/stop.service';
import {StopMapperService} from '../services/stop-mapper.service';
import {CRUD_MAPPER_TOKEN, CRUD_SERVICE_TOKEN} from '../directives/base-crud.component';
import {ChangeDetectorRef} from '@angular/core';

describe('StopComponent', () => {
  let component: StopComponent;
  let stopServiceSpy: jasmine.SpyObj<StopService>;
  let stopMapperSpy: jasmine.SpyObj<StopMapperService>;

  beforeEach(() => {
    const stopServiceMock = jasmine.createSpyObj('StopService', ['getAll', 'create', 'update', 'delete']);
    const stopMapperMock = jasmine.createSpyObj('StopMapperService', ['mapToView']);

    TestBed.configureTestingModule({
      providers: [
        StopComponent,
        {provide: CRUD_SERVICE_TOKEN, useValue: stopServiceMock},
        {provide: CRUD_MAPPER_TOKEN, useValue: stopMapperMock}, {
          provide: ChangeDetectorRef,
          useValue: {
            detectChanges: () => {
            }
          }
        }
      ]
    });

    component = TestBed.inject(StopComponent);
    stopServiceSpy = TestBed.inject(CRUD_SERVICE_TOKEN) as jasmine.SpyObj<StopService>;
    stopMapperSpy = TestBed.inject(CRUD_MAPPER_TOKEN) as jasmine.SpyObj<StopMapperService>;
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  describe('setupTableColumns', () => {
    it('should map stop to table columns', () => {
      const mappedView = [{label: 'Stop Name', value: 'Main Street'}, {label: 'Address', value: '123 Street'}];
      stopMapperSpy.mapToView.and.returnValue(mappedView);

      component['setupTableColumns']();

      expect(component.tableColumns.length).toBe(2);
      expect(component.tableColumns[0]).toEqual({label: 'Stop Name', value: 'stopname'});
      expect(component.tableColumns[1]).toEqual({label: 'Address', value: 'address'});
      expect(stopMapperSpy.mapToView).toHaveBeenCalled();
    });
  });

  describe('createNewItem', () => {
    it('should return a new Stop object with default values', () => {
      const newItem = component.createNewItem();

      expect(newItem).toEqual({
        id: 0,
        stopName: '',
        address: {id: 0, street: '', city: '', buildingNumber: '', postalCode: ''}
      });
    });
  });

  describe('flattenItemForTable', () => {
    it('should flatten stop to key-value pairs', () => {
      const stop = {
        id: 1,
        stopName: 'Central',
        address: {id: 1, street: 'Main', city: 'City', buildingNumber: '10', postalCode: '12345'}
      };
      const mappedView = [
        {label: 'Stop Name', value: 'Central'},
        {label: 'Address', value: 'Main, City, 10, 12345'}
      ];
      stopMapperSpy.mapToView.and.returnValue(mappedView);

      const flattened = component.flattenItemForTable(stop);

      expect(flattened).toEqual({stopname: 'Central', address: 'Main, City, 10, 12345'});
      expect(stopMapperSpy.mapToView).toHaveBeenCalledWith(stop);
    });
  });
});
