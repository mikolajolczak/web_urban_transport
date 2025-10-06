package com.example.demo.service;

import com.example.demo.entity.Office;
import com.example.demo.entity.Vehicle;
import com.example.demo.repository.OfficeRepository;
import com.example.demo.repository.VehicleRepository;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VehicleServiceTest {

  @Mock
  private VehicleRepository vehicleRepository;

  @Mock
  private OfficeRepository officeRepository;

  @InjectMocks
  private VehicleService vehicleService;

  private Vehicle vehicle;
  private Office office;

  private AutoCloseable mocks;

  @BeforeEach
  void setUp() {
    mocks = MockitoAnnotations.openMocks(this);
    office = new Office();
    office.setId(1);
    office.setOfficeName("Main Office");

    vehicle = new Vehicle();
    vehicle.setId(1);
    vehicle.setBrand("Toyota");
    vehicle.setModel("Corolla");
    vehicle.setInsuranceDate(Date.valueOf(LocalDate.of(2023, 1, 1)));
    vehicle.setProductionYear(2020);
    vehicle.setRegistrationNumber("ABC123");
    vehicle.setPurchaseDate(Date.valueOf(LocalDate.of(2020, 5, 10)));
    vehicle.setOffice(office);
  }

  @AfterEach
  void tearDown() throws Exception {
    mocks.close();
  }

  @Test
  void findAll_ShouldReturnAllVehicles() {
    when(vehicleRepository.findAll()).thenReturn(
        Collections.singletonList(vehicle));
    List<Vehicle> result = vehicleService.findAll();
    assertEquals(1, result.size());
    verify(vehicleRepository, times(1)).findAll();
  }

  @Test
  void findById_ShouldReturnVehicle_WhenExists() {
    when(vehicleRepository.findById(1)).thenReturn(Optional.of(vehicle));
    Optional<Vehicle> result = vehicleService.findById(1);
    assertTrue(result.isPresent());
    assertEquals(vehicle.getBrand(), result.get().getBrand());
    verify(vehicleRepository, times(1)).findById(1);
  }

  @Test
  void findById_ShouldReturnEmpty_WhenNotExists() {
    when(vehicleRepository.findById(2)).thenReturn(Optional.empty());
    Optional<Vehicle> result = vehicleService.findById(2);
    assertFalse(result.isPresent());
    verify(vehicleRepository, times(1)).findById(2);
  }

  @Test
  void save_ShouldPersistVehicle_WhenOfficeExists() {
    when(officeRepository.findById(1)).thenReturn(Optional.of(office));
    when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);
    Vehicle result = vehicleService.save(vehicle);
    assertEquals(vehicle.getBrand(), result.getBrand());
    verify(officeRepository, times(1)).findById(1);
    verify(vehicleRepository, times(1)).save(vehicle);
  }

  @Test
  void save_ShouldThrowException_WhenOfficeNotFound() {
    when(officeRepository.findById(1)).thenReturn(Optional.empty());
    RuntimeException exception = assertThrows(RuntimeException.class,
        () -> vehicleService.save(vehicle));
    assertEquals("Office not found", exception.getMessage());
    verify(vehicleRepository, never()).save(any());
  }

  @Test
  void update_ShouldModifyExistingVehicle_WhenExists() {
    Vehicle updatedVehicle = new Vehicle();
    updatedVehicle.setBrand("Honda");
    updatedVehicle.setModel("Civic");
    updatedVehicle.setInsuranceDate(Date.valueOf(LocalDate.of(2024, 1, 1)));
    updatedVehicle.setProductionYear(2021);
    updatedVehicle.setRegistrationNumber("XYZ789");
    updatedVehicle.setPurchaseDate(Date.valueOf(LocalDate.of(2021, 3, 5)));
    updatedVehicle.setOffice(office);

    when(vehicleRepository.findById(1)).thenReturn(Optional.of(vehicle));
    when(officeRepository.findById(1)).thenReturn(Optional.of(office));
    when(vehicleRepository.save(any(Vehicle.class))).thenReturn(updatedVehicle);

    Optional<Vehicle> result = vehicleService.update(1, updatedVehicle);

    assertTrue(result.isPresent());
    assertEquals("Honda", result.get().getBrand());
    assertEquals("XYZ789", result.get().getRegistrationNumber());
    verify(vehicleRepository, times(1)).findById(1);
    verify(officeRepository, times(1)).findById(1);
    verify(vehicleRepository, times(1)).save(vehicle);
  }

  @Test
  void update_ShouldReturnEmpty_WhenVehicleNotFound() {
    Vehicle updatedVehicle = new Vehicle();
    when(vehicleRepository.findById(2)).thenReturn(Optional.empty());
    Optional<Vehicle> result = vehicleService.update(2, updatedVehicle);
    assertFalse(result.isPresent());
    verify(vehicleRepository, times(1)).findById(2);
    verify(vehicleRepository, never()).save(any());
  }

  @Test
  void update_ShouldThrowException_WhenOfficeNotFound() {
    Vehicle updatedVehicle = new Vehicle();
    updatedVehicle.setOffice(office);
    when(vehicleRepository.findById(1)).thenReturn(Optional.of(vehicle));
    when(officeRepository.findById(1)).thenReturn(Optional.empty());
    RuntimeException exception = assertThrows(RuntimeException.class,
        () -> vehicleService.update(1, updatedVehicle));
    assertEquals("Office not found", exception.getMessage());
    verify(vehicleRepository, times(1)).findById(1);
    verify(vehicleRepository, never()).save(any());
  }

  @Test
  void deleteById_ShouldReturnTrue_WhenVehicleExists() {
    when(vehicleRepository.existsById(1)).thenReturn(true);
    boolean result = vehicleService.deleteById(1);
    assertTrue(result);
    verify(vehicleRepository, times(1)).deleteById(1);
  }

  @Test
  void deleteById_ShouldReturnFalse_WhenVehicleNotExists() {
    when(vehicleRepository.existsById(2)).thenReturn(false);
    boolean result = vehicleService.deleteById(2);
    assertFalse(result);
    verify(vehicleRepository, never()).deleteById(2);
  }
}