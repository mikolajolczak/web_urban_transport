package com.example.demo.service;

import com.example.demo.entity.Address;
import com.example.demo.repository.AddressRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddressServiceTest {

  @Mock
  private AddressRepository addressRepository;

  @InjectMocks
  private AddressService addressService;

  private AutoCloseable mocks;

  @BeforeEach
  void setUp() {
    mocks = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() throws Exception {
    mocks.close();
  }

  @Test
  void testSave() {
    Address address = new Address();
    address.setId(1);
    address.setCity("New York");

    when(addressRepository.save(address)).thenReturn(address);

    Address savedAddress = addressService.save(address);

    assertNotNull(savedAddress);
    assertEquals(1, savedAddress.getId());
    assertEquals("New York", savedAddress.getCity());
    verify(addressRepository, times(1)).save(address);
  }

  @Test
  void testFindAll() {
    Address address1 = new Address();
    address1.setId(1);
    address1.setCity("New York");

    Address address2 = new Address();
    address2.setId(2);
    address2.setCity("Los Angeles");

    List<Address> addresses = Arrays.asList(address1, address2);

    when(addressRepository.findAll()).thenReturn(addresses);

    List<Address> result = addressService.findAll();

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("New York", result.get(0).getCity());
    assertEquals("Los Angeles", result.get(1).getCity());
    verify(addressRepository, times(1)).findAll();
  }

  @Test
  void testFindByIdFound() {
    Address address = new Address();
    address.setId(1);
    address.setCity("New York");

    when(addressRepository.findById(1)).thenReturn(Optional.of(address));

    Optional<Address> result = addressService.findById(1);

    assertTrue(result.isPresent());
    assertEquals("New York", result.get().getCity());
    verify(addressRepository, times(1)).findById(1);
  }

  @Test
  void testFindByIdNotFound() {
    when(addressRepository.findById(1)).thenReturn(Optional.empty());

    Optional<Address> result = addressService.findById(1);

    assertFalse(result.isPresent());
    verify(addressRepository, times(1)).findById(1);
  }
}
