package com.example.demo.repository;

import com.example.demo.entity.Address;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Address} entities.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

  /**
   * Finds an address by the given street, city, postal code, building number
   * and apartment number.
   *
   * @param street          the street name
   * @param city            the city name
   * @param postalCode      the postal code
   * @param buildingNumber  the building number
   * @param apartmentNumber the apartment number
   * @return an {@link Optional} containing the matching address, if found
   */
  Optional<Address> findByStreetAndCityAndPostalCodeAndBuildingNumberAndApartmentNumber(
      String street,
      String city,
      String postalCode,
      String buildingNumber,
      String apartmentNumber
  );
}
