package com.example.demo.service;

import com.example.demo.entity.Office;
import com.example.demo.entity.Address;
import com.example.demo.repository.OfficeRepository;
import com.example.demo.repository.AddressRepository;
import java.util.List;
import java.util.Objects;
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
    return officeRepository.findById(id).map(existing -> {
      existing.setOfficeName(updatedOffice.getOfficeName());

      Address existingAddress = existing.getAddress();
      Address updatedAddress = updatedOffice.getAddress();

      if (!areAddressesEqual(existingAddress, updatedAddress)) {
        existingAddress.setStreet(updatedAddress.getStreet());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setBuildingNumber(updatedAddress.getBuildingNumber());
        existingAddress.setApartmentNumber(updatedAddress.getApartmentNumber());
        existingAddress.setPostalCode(updatedAddress.getPostalCode());

        addressRepository.save(existingAddress);
      }

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