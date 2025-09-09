package com.example.demo.service;


import com.example.demo.entity.Address;
import com.example.demo.repository.AddressRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
  private final AddressRepository addressRepository;

  public AddressService(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  public Address save(Address address) {
    return addressRepository.save(address);
  }

  public List<Address> findAll() {
    return addressRepository.findAll();
  }
  public Optional<Address> findById(int id) {
    return addressRepository.findById(id);
  }
}
