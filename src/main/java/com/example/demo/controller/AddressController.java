package com.example.demo.controller;

import com.example.demo.entity.Address;
import com.example.demo.service.AddressService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
  private final AddressService addressService;

  public AddressController(AddressService addressService) {
    this.addressService = addressService;
  }

  @PostMapping
  public ResponseEntity<Address> saveAddress(@Validated @RequestBody
                                             Address address) {
    Address saved = addressService.save(address);
    return ResponseEntity.ok(saved);
  }

  @GetMapping
  public ResponseEntity<List<Address>> getAllAddresses() {
    return ResponseEntity.ok(addressService.findAll());
  }

  @GetMapping
  @RequestMapping("/{id}")
  public ResponseEntity<Address> getAddress(@PathVariable int id) {
    return addressService.findById(id).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
