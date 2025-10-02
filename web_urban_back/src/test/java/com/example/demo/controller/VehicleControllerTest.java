package com.example.demo.controller;

import com.example.demo.entity.Vehicle;
import com.example.demo.service.VehicleService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VehicleControllerTest {

  @Mock
  private VehicleService vehicleService;

  @InjectMocks
  private VehicleController vehicleController;

  private Vehicle vehicle1;
  private Vehicle vehicle2;

  private AutoCloseable mocks;

  @BeforeEach
  void setUp() {
    mocks = MockitoAnnotations.openMocks(this);
    vehicle1 = new Vehicle();
    vehicle1.setId(1);
    vehicle1.setBrand("Toyota");
    vehicle1.setModel("Corolla");
    vehicle2 = new Vehicle();
    vehicle2.setId(2);
    vehicle2.setBrand("Honda");
    vehicle2.setModel("Civic");
  }

  @AfterEach
  void tearDown() throws Exception {
    mocks.close();
  }

  @Test
  void testFindAllVehicles() {
    List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
    when(vehicleService.findAll()).thenReturn(vehicles);

    ResponseEntity<List<Vehicle>> response =
        vehicleController.findAllVehicles();

    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());
    verify(vehicleService, times(1)).findAll();
  }

  @Test
  void testFindVehicleById_Found() {
    when(vehicleService.findById(1)).thenReturn(Optional.of(vehicle1));

    ResponseEntity<Vehicle> response = vehicleController.findVehicleById(1);

    assertEquals(200, response.getStatusCode().value());
    assertEquals(vehicle1, response.getBody());
    verify(vehicleService, times(1)).findById(1);
  }

  @Test
  void testFindVehicleById_NotFound() {
    when(vehicleService.findById(3)).thenReturn(Optional.empty());

    ResponseEntity<Vehicle> response = vehicleController.findVehicleById(3);

    assertEquals(404, response.getStatusCode().value());
    assertNull(response.getBody());
    verify(vehicleService, times(1)).findById(3);
  }

  @Test
  void testSaveVehicle() {
    when(vehicleService.save(vehicle1)).thenReturn(vehicle1);

    ResponseEntity<Vehicle> response = vehicleController.saveVehicle(vehicle1);

    assertEquals(200, response.getStatusCode().value());
    assertEquals(vehicle1, response.getBody());
    verify(vehicleService, times(1)).save(vehicle1);
  }

  @Test
  void testUpdateVehicle_Found() {
    Vehicle updatedVehicle = new Vehicle();
    updatedVehicle.setId(1);
    updatedVehicle.setBrand("Toyota");
    updatedVehicle.setModel("Camry");

    when(vehicleService.update(1, updatedVehicle)).thenReturn(
        Optional.of(updatedVehicle));

    ResponseEntity<Vehicle> response =
        vehicleController.updateVehicle(updatedVehicle, 1);

    assertEquals(200, response.getStatusCode().value());
    assertEquals(updatedVehicle, response.getBody());
    verify(vehicleService, times(1)).update(1, updatedVehicle);
  }

  @Test
  void testUpdateVehicle_NotFound() {
    Vehicle updatedVehicle = new Vehicle();
    updatedVehicle.setId(3);
    updatedVehicle.setBrand("Ford");
    updatedVehicle.setModel("Focus");

    when(vehicleService.update(3, updatedVehicle)).thenReturn(Optional.empty());

    ResponseEntity<Vehicle> response =
        vehicleController.updateVehicle(updatedVehicle, 3);

    assertEquals(404, response.getStatusCode().value());
    assertNull(response.getBody());
    verify(vehicleService, times(1)).update(3, updatedVehicle);
  }

  @Test
  void testDeleteVehicle_Found() {
    when(vehicleService.deleteById(1)).thenReturn(true);

    ResponseEntity<?> response = vehicleController.deleteVehicle(1);

    assertEquals(200, response.getStatusCode().value());
    verify(vehicleService, times(1)).deleteById(1);
  }

  @Test
  void testDeleteVehicle_NotFound() {
    when(vehicleService.deleteById(3)).thenReturn(false);

    ResponseEntity<?> response = vehicleController.deleteVehicle(3);

    assertEquals(404, response.getStatusCode().value());
    verify(vehicleService, times(1)).deleteById(3);
  }
}
