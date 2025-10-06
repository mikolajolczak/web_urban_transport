package com.example.demo.service;

import static com.example.demo.service.AddressService.updateAddressIfChanged;

import com.example.demo.entity.Address;
import com.example.demo.entity.Stop;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.StopRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class for managing {@link Stop} entities.
 *
 * <p>This service provides methods to find, save, update, and delete stops.
 * Each stop is associated with an {@link Address}. If an address already exists
 * in the database, it will be reused; otherwise, a new address will be created.
 * </p>
 */
@Service
public class StopService {

  /**
   * Repository for performing CRUD operations on {@link Stop} entities.
   */
  private final StopRepository stopRepository;

  /**
   * Repository for performing CRUD operations on {@link Address} entities.
   */
  private final AddressRepository addressRepository;

  /**
   * Constructs a new {@link StopService} with the given repositories.
   *
   * @param stopRepositoryParam    the repository for managing Stop entities
   * @param addressRepositoryParam the repository for managing Address entities
   */
  public StopService(final StopRepository stopRepositoryParam,
                     final AddressRepository addressRepositoryParam) {
    this.stopRepository = stopRepositoryParam;
    this.addressRepository = addressRepositoryParam;
  }

  /**
   * Retrieves all stops from the database.
   *
   * @return a list of all {@link Stop} entities
   */
  public List<Stop> findAll() {
    return stopRepository.findAll();
  }

  /**
   * Retrieves a stop by its unique identifier.
   *
   * @param id the ID of the stop to retrieve
   * @return an {@link Optional} containing the found stop, or empty if not
   *     found
   */
  public Optional<Stop> findById(final int id) {
    return stopRepository.findById(id);
  }

  /**
   * Saves a new stop or updates an existing stop.
   *
   * <p>If the address of the stop already exists, it will be reused.
   * </p>
   *
   * @param stop the stop entity to save
   * @return the saved {@link Stop} entity
   */
  public Stop save(final Stop stop) {
    Address existingAddress = findOrCreateAddress(stop.getAddress());
    stop.setAddress(existingAddress);
    return stopRepository.save(stop);
  }

  /**
   * Deletes a stop by its unique identifier.
   *
   * @param id the ID of the stop to delete
   * @return {@code true} if the stop was found and deleted, {@code false}
   *     otherwise
   */
  public boolean deleteById(final int id) {
    if (stopRepository.existsById(id)) {
      stopRepository.deleteById(id);
      return true;
    }
    return false;
  }

  /**
   * Updates an existing stop with new data.
   *
   * <p>Only the stop name and address are updated. If the address is modified,
   * {@link AddressService#updateAddressIfChanged} is called to persist changes.
   * </p>
   *
   * @param id          the ID of the stop to update
   * @param updatedStop the updated stop data
   * @return an {@link Optional} containing the updated stop if found, or
   *     empty if not found
   */
  public Optional<Stop> update(final int id, final Stop updatedStop) {
    return stopRepository.findById(id).map(existing -> {
      existing.setStopName(updatedStop.getStopName());

      Address existingAddress = existing.getAddress();
      Address updatedAddress = updatedStop.getAddress();

      updateAddressIfChanged(existingAddress, updatedAddress,
          addressRepository);

      return stopRepository.save(existing);
    });
  }

  /**
   * Finds an existing address matching the given address fields, or creates
   * a new one if none exists.
   *
   * @param address the address to find or create
   * @return the existing or newly created {@link Address} entity, or {@code
   *     null} if the input is null
   */
  private Address findOrCreateAddress(final Address address) {
    if (address == null) {
      return null;
    }

    Optional<Address> existingAddress = addressRepository
        .findByStreetAndCityAndPostalCodeAndBuildingNumberAndApartmentNumber(
            address.getStreet(),
            address.getCity(),
            address.getPostalCode(),
            address.getBuildingNumber(),
            address.getApartmentNumber()
        );

    return existingAddress.orElseGet(() -> addressRepository.save(address));
  }
}
