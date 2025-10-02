package com.example.demo.repository;


import com.example.demo.entity.Address;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
  Optional<Address> findByStreetAndCityAndPostalCodeAndBuildingNumberAndApartmentNumber(String street, String city, String postalCode, String buildingNumber, String apartmentNumber);

}
