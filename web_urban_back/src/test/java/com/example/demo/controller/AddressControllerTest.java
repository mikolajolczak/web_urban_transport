package com.example.demo.controller;

import com.example.demo.entity.Address;
import com.example.demo.service.AddressService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddressControllerTest {

  @Mock
  private AddressService addressService;

  @InjectMocks
  private AddressController addressController;

  private Address address1;
  private Address address2;

  private AutoCloseable mocks;

  @BeforeEach
  void setUp() {
    mocks = MockitoAnnotations.openMocks(this);
    address1 = new Address();
    address1.setId(1);
    address1.setStreet("123 Main St");
    address2 = new Address();
    address2.setId(2);
    address2.setStreet("456 Elm St");
  }

  @AfterEach
  void tearDown() throws Exception {
    mocks.close();
  }

  @Test
  void testSaveAddress() {
    when(addressService.save(address1)).thenReturn(address1);

    ResponseEntity<Address> response = addressController.saveAddress(address1);

    assertNotNull(response);
    assertEquals(200, response.getStatusCode().value());
    assertEquals(address1, response.getBody());
    verify(addressService, times(1)).save(address1);
  }

  @Test
  void testGetAllAddresses() {
    List<Address> addresses = Arrays.asList(address1, address2);
    when(addressService.findAll()).thenReturn(addresses);

    ResponseEntity<List<Address>> response =
        addressController.getAllAddresses();

    assertNotNull(response);
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());
    assertEquals(addresses, response.getBody());
    verify(addressService, times(1)).findAll();
  }

  @Test
  void testGetAddressFound() {
    when(addressService.findById(1)).thenReturn(Optional.of(address1));

    ResponseEntity<Address> response = addressController.getAddress(1);

    assertNotNull(response);
    assertEquals(200, response.getStatusCode().value());
    assertEquals(address1, response.getBody());
    verify(addressService, times(1)).findById(1);
  }

  @Test
  void testGetAddressNotFound() {
    when(addressService.findById(3)).thenReturn(Optional.empty());

    ResponseEntity<Address> response = addressController.getAddress(3);

    assertNotNull(response);
    assertEquals(404, response.getStatusCode().value());
    assertNull(response.getBody());
    verify(addressService, times(1)).findById(3);
  }
}
