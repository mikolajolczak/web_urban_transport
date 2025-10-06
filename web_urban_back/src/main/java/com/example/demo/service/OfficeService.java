package com.example.demo.service;

import static com.example.demo.service.AddressService.updateAddressIfChanged;

import com.example.demo.entity.Address;
import com.example.demo.entity.Office;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.OfficeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class for managing {@link Office} entities.
 *
 * <p>This service handles CRUD operations and ensures that addresses
 * are reused when possible to avoid duplicate entries.
 * </p>
 */
@Service
public class OfficeService {
  /**
   * Repository for performing CRUD operations on {@link Office} entities.
   */
  private final OfficeRepository officeRepository;
  /**
   * Repository for performing CRUD operations on {@link Address} entities.
   */
  private final AddressRepository addressRepository;

  /**
   * Constructs a new {@link OfficeService} with the given repositories.
   *
   * @param officeRepositoryParam  the repository managing Office entities
   * @param addressRepositoryParam the repository managing Address entities
   */
  public OfficeService(final OfficeRepository officeRepositoryParam,
                       final AddressRepository addressRepositoryParam) {
    this.officeRepository = officeRepositoryParam;
    this.addressRepository = addressRepositoryParam;
  }

  /**
   * Retrieves all offices.
   *
   * @return a list of all {@link Office} entities
   */
  public List<Office> findAll() {
    return officeRepository.findAll();
  }

  /**
   * Deletes an office by its ID if it exists.
   *
   * @param id the ID of the office to delete
   * @return true if the office was found and deleted; false otherwise
   */
  public boolean deleteById(final int id) {
    if (officeRepository.existsById(id)) {
      officeRepository.deleteById(id);
      return true;
    }
    return false;
  }

  /**
   * Finds an office by its ID.
   *
   * @param id the ID of the office
   * @return an {@link Optional} containing the office if found
   */
  public Optional<Office> findById(final int id) {
    return officeRepository.findById(id);
  }

  /**
   * Updates an existing office with new data.
   *
   * <p>Both the office's name and its associated address will be updated
   * if there are changes.</p>
   *
   * @param id            the ID of the office to update
   * @param updatedOffice the updated office data
   * @return an {@link Optional} containing the updated office if it exists
   */
  public Optional<Office> updateOffice(final int id,
                                       final Office updatedOffice) {
    return officeRepository.findById(id).map(existing -> {
      existing.setOfficeName(updatedOffice.getOfficeName());

      updateAddressIfChanged(existing.getAddress(), updatedOffice.getAddress(),
          addressRepository);

      return officeRepository.save(existing);
    });
  }

  /**
   * Saves a new office or updates an existing one if already present.
   *
   * <p>The address is checked for existing duplicates to avoid redundant
   * entries.</p>
   *
   * @param office the office to save
   * @return the saved {@link Office} entity
   */
  public Office save(final Office office) {
    Address existingAddress = findOrCreateAddress(office.getAddress());
    office.setAddress(existingAddress);
    return officeRepository.save(office);
  }

  /**
   * Finds an existing address matching the provided details, or creates a
   * new one if none exists.
   *
   * @param address the address to find or create
   * @return the existing or newly created {@link Address}
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
