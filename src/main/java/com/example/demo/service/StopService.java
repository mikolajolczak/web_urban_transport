package com.example.demo.service;

import com.example.demo.entity.Address;
import com.example.demo.entity.Stop;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.StopRepository;
import java.util.List;
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

      Address oldAddress = updatedStop.getAddress();
      Address newAddress = new Address();
      newAddress.setStreet(oldAddress.getStreet());
      newAddress.setCity(oldAddress.getCity());
      newAddress.setBuildingNumber(oldAddress.getBuildingNumber());
      newAddress.setApartmentNumber(oldAddress.getApartmentNumber());
      newAddress.setPostalCode(oldAddress.getPostalCode());

      Address savedAddress = addressRepository.save(newAddress);
      existing.setAddress(savedAddress);

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
}
