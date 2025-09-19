package com.example.demo.service;

import com.example.demo.entity.Address;
import com.example.demo.entity.Stop;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.StopRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StopService {

  private final StopRepository stopRepository;
  private final AddressRepository addressRepository;

  public StopService(StopRepository stopRepository, AddressRepository addressRepository) {
    this.stopRepository = stopRepository;
    this.addressRepository = addressRepository;
  }

  public List<Stop> findAll() {
    return stopRepository.findAll();
  }

  public Optional<Stop> findById(Integer id) {
    return stopRepository.findById(id);
  }

  public Stop save(Stop stop) {
    Address existingAddress = findOrCreateAddress(stop.getAddress());
    stop.setAddress(existingAddress);
    return stopRepository.save(stop);
  }

  public boolean deleteById(Integer id) {
    if (stopRepository.existsById(id)) {
      stopRepository.deleteById(id);
      return true;
    }
    return false;
  }

  public Optional<Stop> update(Integer id, Stop updatedStop) {
    return stopRepository.findById(id).map(existing -> {
      existing.setStopName(updatedStop.getStopName());

      Address existingAddress = existing.getAddress();
      Address updatedAddress = updatedStop.getAddress();

      if (!areAddressesEqual(existingAddress, updatedAddress)) {
        existingAddress.setStreet(updatedAddress.getStreet());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setBuildingNumber(updatedAddress.getBuildingNumber());
        existingAddress.setApartmentNumber(updatedAddress.getApartmentNumber());
        existingAddress.setPostalCode(updatedAddress.getPostalCode());

        addressRepository.save(existingAddress);
      }

      return stopRepository.save(existing);
    });
  }

  private Address findOrCreateAddress(Address address) {
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
  private boolean areAddressesEqual(Address a, Address b) {
    if (a == b) return true;
    if (a == null || b == null) return false;
    return Objects.equals(a.getStreet(), b.getStreet()) &&
        Objects.equals(a.getCity(), b.getCity()) &&
        Objects.equals(a.getBuildingNumber(), b.getBuildingNumber()) &&
        Objects.equals(a.getApartmentNumber(), b.getApartmentNumber()) &&
        Objects.equals(a.getPostalCode(), b.getPostalCode());
  }
}
