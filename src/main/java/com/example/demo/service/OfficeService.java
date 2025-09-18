package com.example.demo.service;

import com.example.demo.entity.Office;
import com.example.demo.entity.Address;
import com.example.demo.repository.OfficeRepository;
import com.example.demo.repository.AddressRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OfficeService {
  private final OfficeRepository officeRepository;
  private final AddressRepository addressRepository;

  public OfficeService(OfficeRepository officeRepository, AddressRepository addressRepository) {
    this.officeRepository = officeRepository;
    this.addressRepository = addressRepository;
  }

  public List<Office> findAll() {
    return officeRepository.findAll();
  }

  public boolean deleteById(Integer id) {
    if (officeRepository.existsById(id)) {
      officeRepository.deleteById(id);
      return true;
    }
    return false;
  }

  public Optional<Office> findById(Integer id) {
    return officeRepository.findById(id);
  }

  public Optional<Office> updateOffice(int id, Office updatedOffice) {
    return officeRepository.findById(id).map((existing) -> {
      existing.setOfficeName(updatedOffice.getOfficeName());

      Address oldAddress = updatedOffice.getAddress();
      Address newAddress = new Address();
      newAddress.setStreet(oldAddress.getStreet());
      newAddress.setCity(oldAddress.getCity());
      newAddress.setBuildingNumber(oldAddress.getBuildingNumber());
      newAddress.setApartmentNumber(oldAddress.getApartmentNumber());
      newAddress.setPostalCode(oldAddress.getPostalCode());

      Address savedAddress = addressRepository.save(newAddress);
      existing.setAddress(savedAddress);

      return officeRepository.save(existing);
    });
  }

  public Office save(Office office) {
    Address existingAddress = findOrCreateAddress(office.getAddress());
    office.setAddress(existingAddress);

    return officeRepository.save(office);
  }

  private Address findOrCreateAddress(Address address) {
    if (address == null) {
      return null;
    }

    Optional<Address> existingAddress = addressRepository.findByStreetAndCityAndPostalCodeAndBuildingNumberAndApartmentNumber(
        address.getStreet(),
        address.getCity(),
        address.getPostalCode(),
        address.getBuildingNumber(),
        address.getApartmentNumber()
    );

    return existingAddress.orElseGet(() -> addressRepository.save(address));
  }
}