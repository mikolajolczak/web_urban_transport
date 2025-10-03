package com.example.demo.controller;

import com.example.demo.entity.Vehicle;
import com.example.demo.service.VehicleService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link Vehicle} entities.
 *
 * <p>Provides CRUD operations for vehicles including retrieval, creation,
 * update, and deletion.
 * </p>
 */
@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {
  /**
   * Service layer for managing {@link Vehicle} entities.
   */
  private final VehicleService vehicleService;

  /**
   * Constructs a new {@code VehicleController} with the specified
   * {@link VehicleService}.
   *
   * @param vehicleServiceParam the vehicle service, must not be {@code null}
   */
  public VehicleController(final VehicleService vehicleServiceParam) {
    this.vehicleService = vehicleServiceParam;
  }

  /**
   * Retrieves all vehicles.
   *
   * @return a {@link ResponseEntity} containing the list of all vehicles
   */
  @GetMapping
  public ResponseEntity<List<Vehicle>> findAllVehicles() {
    return ResponseEntity.ok(vehicleService.findAll());
  }

  /**
   * Retrieves a vehicle by its unique ID.
   *
   * @param id the vehicle ID, must be positive
   * @return a {@link ResponseEntity} containing the vehicle if found,
   *     or {@code 404 Not Found} if the vehicle does not exist
   */
  @GetMapping("/{id}")
  public ResponseEntity<Vehicle> findVehicleById(final @PathVariable int id) {
    return vehicleService.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Creates a new vehicle.
   *
   * @param vehicle the vehicle to create, must not be {@code null}
   * @return a {@link ResponseEntity} containing the created vehicle
   */
  @PostMapping
  public ResponseEntity<Vehicle> saveVehicle(
      final @RequestBody Vehicle vehicle) {
    return ResponseEntity.ok(vehicleService.save(vehicle));
  }

  /**
   * Updates an existing vehicle identified by its ID.
   *
   * @param vehicle the vehicle data to update, must not be {@code null}
   * @param id      the ID of the vehicle to update
   * @return a {@link ResponseEntity} containing the updated vehicle if found,
   *     or {@code 404 Not Found} if the vehicle does not exist
   */
  @PutMapping("/{id}")
  public ResponseEntity<Vehicle> updateVehicle(
      final @RequestBody Vehicle vehicle,
      final @PathVariable int id) {
    return vehicleService.update(id, vehicle)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Deletes a vehicle by its ID.
   *
   * @param id the ID of the vehicle to delete
   * @return {@code 200 OK} if the vehicle was deleted successfully,
   *     or {@code 404 Not Found} if the vehicle does not exist
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteVehicle(final @PathVariable int id) {
    return vehicleService.deleteById(id)
        ? ResponseEntity.ok().build()
        : ResponseEntity.notFound().build();
  }
}
