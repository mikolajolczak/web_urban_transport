package com.example.demo.service;

import com.example.demo.entity.Office;
import com.example.demo.entity.Vehicle;
import com.example.demo.repository.OfficeRepository;
import com.example.demo.repository.VehicleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing {@link Vehicle} entities.
 * Provides methods for CRUD operations including save, update, find, and
 * delete.
 */
@Service
public class VehicleService {

  /**
   * Repository for performing CRUD operations on {@link Vehicle} entities.
   */
  private final VehicleRepository vehicleRepository;

  /**
   * Repository for performing CRUD operations on {@link Office} entities.
   */
  private final OfficeRepository officeRepository;

  /**
   * Constructs a {@code VehicleService} with the given repositories.
   *
   * @param vehicleRepositoryParam the repository for {@link Vehicle} entities
   * @param officeRepositoryParam  the repository for {@link Office} entities
   */
  public VehicleService(final VehicleRepository vehicleRepositoryParam,
                        final OfficeRepository officeRepositoryParam) {
    this.vehicleRepository = vehicleRepositoryParam;
    this.officeRepository = officeRepositoryParam;
  }

  /**
   * Retrieves all {@link Vehicle} entities from the repository.
   *
   * @return a list of all vehicles
   */
  public List<Vehicle> findAll() {
    return vehicleRepository.findAll();
  }

  /**
   * Finds a {@link Vehicle} by its identifier.
   *
   * @param id the id of the vehicle
   * @return an {@link Optional} containing the vehicle if found, or empty
   *     otherwise
   */
  public Optional<Vehicle> findById(final Integer id) {
    return vehicleRepository.findById(id);
  }

  /**
   * Saves a new {@link Vehicle} entity.
   * Ensures that the associated {@link Office} exists before saving.
   *
   * @param vehicle the vehicle to save
   * @return the saved vehicle
   * @throws RuntimeException if the office associated with the vehicle does
   *                          not exist
   */
  public Vehicle save(final Vehicle vehicle) {
    final Office managedOffice =
        officeRepository.findById(vehicle.getOffice().getId())
            .orElseThrow(() -> new RuntimeException("Office not found"));
    vehicle.setOffice(managedOffice);
    return vehicleRepository.save(vehicle);
  }

  /**
   * Updates an existing {@link Vehicle} entity.
   * All vehicle fields, including its associated office, will be updated.
   *
   * @param id             the id of the vehicle to update
   * @param updatedVehicle the vehicle object containing updated data
   * @return an {@link Optional} containing the updated vehicle, or empty if
   *     no vehicle with the given id exists
   * @throws RuntimeException if the office associated with the updated
   *                          vehicle does not exist
   */
  public Optional<Vehicle> update(final int id, final Vehicle updatedVehicle) {
    return vehicleRepository.findById(id).map(existing -> {
      existing.setBrand(updatedVehicle.getBrand());
      existing.setModel(updatedVehicle.getModel());
      existing.setInsuranceDate(updatedVehicle.getInsuranceDate());
      existing.setProductionYear(updatedVehicle.getProductionYear());
      existing.setRegistrationNumber(updatedVehicle.getRegistrationNumber());
      existing.setPurchaseDate(updatedVehicle.getPurchaseDate());

      final Office managedOffice =
          officeRepository.findById(updatedVehicle.getOffice().getId())
              .orElseThrow(() -> new RuntimeException("Office not found"));
      existing.setOffice(managedOffice);

      return vehicleRepository.save(existing);
    });
  }

  /**
   * Deletes a {@link Vehicle} entity by its id.
   *
   * @param id the id of the vehicle to delete
   * @return {@code true} if the vehicle was found and deleted; {@code false}
   *     otherwise
   */
  public boolean deleteById(final int id) {
    if (vehicleRepository.existsById(id)) {
      vehicleRepository.deleteById(id);
      return true;
    }
    return false;
  }
}
