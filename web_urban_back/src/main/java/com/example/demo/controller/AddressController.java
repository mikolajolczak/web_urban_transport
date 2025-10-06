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

/**
 * REST controller that exposes CRUD endpoints for managing {@link Address}
 * entities.
 *
 * <p>This controller provides endpoints to create a new address and to fetch
 * existing addresses.
 * </p>
 */
@RestController
@RequestMapping("/api/address")
public class AddressController {
  /**
   * Service layer component used for managing {@link Address} entities.
   */
  private final AddressService addressService;

  /**
   * Creates a new {@code AddressController} with the given
   * {@link AddressService}.
   *
   * @param addressServiceParam the service used to handle address operations
   */
  public AddressController(final AddressService addressServiceParam) {
    this.addressService = addressServiceParam;
  }

  /**
   * Persists a new {@link Address}.
   *
   * @param address the address data to save; must be valid
   * @return {@link ResponseEntity} containing the saved address and HTTP 200
   *     status
   */
  @PostMapping
  public ResponseEntity<Address> saveAddress(
      @Validated @RequestBody final Address address) {
    Address saved = addressService.save(address);
    return ResponseEntity.ok(saved);
  }

  /**
   * Retrieves all stored {@link Address} records.
   *
   * @return {@link ResponseEntity} containing the list of all addresses and
   *     HTTP 200 status
   */
  @GetMapping
  public ResponseEntity<List<Address>> getAllAddresses() {
    return ResponseEntity.ok(addressService.findAll());
  }

  /**
   * Retrieves a single {@link Address} by its unique identifier.
   *
   * @param id the ID of the address to retrieve
   * @return {@link ResponseEntity} containing the found address and HTTP 200
   *     status,
   *     or HTTP 404 status if no address exists with the given ID
   */
  @GetMapping("/{id}")
  public ResponseEntity<Address> getAddress(@PathVariable final int id) {
    return addressService.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
