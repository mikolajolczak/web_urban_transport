package com.example.demo.service;

import com.example.demo.entity.Address;
import com.example.demo.repository.AddressRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class that provides business logic for managing {@link Address}
 * entities.
 *
 * <p>All operations delegate to the {@link AddressRepository}.
 * </p>
 */
@Service
public class AddressService {

  /**
   * Repository used to access and persist {@link Address} entities.
   */
  private final AddressRepository addressRepository;

  /**
   * Constructs a new {@code AddressService} with the specified repository.
   *
   * @param addressRepositoryParam the repository used for data access, must
   *                               not be {@code null}
   */
  public AddressService(final AddressRepository addressRepositoryParam) {
    this.addressRepository = addressRepositoryParam;
  }

  /**
   * Compares two addresses for equality based on all their fields.
   *
   * @param a the first address
   * @param b the second address
   * @return {@code true} if all fields are equal, {@code false} otherwise
   */
  public static boolean areAddressesEqual(final Address a, final Address b) {
    if (a == b) {
      return true;
    }
    if (a == null || b == null) {
      return false;
    }

    return Objects.equals(a.getStreet(), b.getStreet())
        && Objects.equals(a.getCity(), b.getCity())
        && Objects.equals(a.getBuildingNumber(), b.getBuildingNumber())
        && Objects.equals(a.getApartmentNumber(), b.getApartmentNumber())
        && Objects.equals(a.getPostalCode(), b.getPostalCode());
  }

  /**
   * Updates the address of an existing employee if it has changed.
   *
   * @param existingAddress   the existing Address
   * @param updatedAddress    the new address data
   * @param addressRepository repository to save updated address
   */
  public static void updateAddressIfChanged(final Address existingAddress,
                                            final Address updatedAddress,
                                            final AddressRepository addressRepository) {
    if (!areAddressesEqual(existingAddress, updatedAddress)) {
      existingAddress.setStreet(updatedAddress.getStreet());
      existingAddress.setCity(updatedAddress.getCity());
      existingAddress.setBuildingNumber(updatedAddress.getBuildingNumber());
      existingAddress.setApartmentNumber(updatedAddress.getApartmentNumber());
      existingAddress.setPostalCode(updatedAddress.getPostalCode());
      addressRepository.save(existingAddress);
    }
  }

  /**
   * Persists the given {@link Address} entity in the underlying data store.
   *
   * @param address the {@link Address} entity to save, must not be {@code null}
   * @return the persisted {@link Address} entity
   */
  public Address save(final Address address) {
    return addressRepository.save(address);
  }

  /**
   * Retrieves all {@link Address} entities from the data store.
   *
   * @return a {@link List} of all {@link Address} entities, never {@code null}
   */
  public List<Address> findAll() {
    return addressRepository.findAll();
  }

  /**
   * Retrieves an {@link Address} entity by its identifier.
   *
   * @param id the identifier of the {@link Address} entity
   * @return an {@link Optional} containing the found {@link Address},
   *     or {@link Optional#empty()} if no entity is found
   */
  public Optional<Address> findById(final int id) {
    return addressRepository.findById(id);
  }
}
