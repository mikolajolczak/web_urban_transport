package com.example.demo.service;

import com.example.demo.entity.Address;
import com.example.demo.entity.Stop;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.StopRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class StopServiceTest {

  private StopRepository stopRepository;
  private AddressRepository addressRepository;
  private StopService stopService;

  @BeforeEach
  void setUp() {
    stopRepository = mock(StopRepository.class);
    addressRepository = mock(AddressRepository.class);
    stopService = new StopService(stopRepository, addressRepository);
  }

  @Test
  void findAll_ReturnsListOfStops() {
    Stop s1 = new Stop();
    Stop s2 = new Stop();
    when(stopRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

    List<Stop> result = stopService.findAll();

    assertEquals(2, result.size());
    verify(stopRepository).findAll();
  }

  @Test
  void findById_ReturnsStop() {
    Stop stop = new Stop();
    when(stopRepository.findById(1)).thenReturn(Optional.of(stop));

    Optional<Stop> result = stopService.findById(1);

    assertTrue(result.isPresent());
    assertEquals(stop, result.get());
    verify(stopRepository).findById(1);
  }

  @Test
  void findById_ReturnsEmptyWhenNotFound() {
    when(stopRepository.findById(1)).thenReturn(Optional.empty());

    Optional<Stop> result = stopService.findById(1);

    assertFalse(result.isPresent());
    verify(stopRepository).findById(1);
  }

  @Test
  void save_SavesStopWithExistingAddress() {
    Address address = createAddress();
    Stop stop = new Stop();
    stop.setAddress(address);

    when(
        addressRepository.findByStreetAndCityAndPostalCodeAndBuildingNumberAndApartmentNumber(
            anyString(), anyString(), anyString(), anyString(), anyString()))
        .thenReturn(Optional.of(address));
    when(stopRepository.save(stop)).thenReturn(stop);

    Stop saved = stopService.save(stop);

    assertEquals(address, saved.getAddress());
    verify(stopRepository).save(stop);
    verify(addressRepository, never()).save(any());
  }

  @Test
  void save_SavesStopWithNewAddress() {
    Address address = createAddress();
    Stop stop = new Stop();
    stop.setAddress(address);

    when(
        addressRepository.findByStreetAndCityAndPostalCodeAndBuildingNumberAndApartmentNumber(
            anyString(), anyString(), anyString(), anyString(), anyString()))
        .thenReturn(Optional.empty());
    when(addressRepository.save(address)).thenReturn(address);
    when(stopRepository.save(stop)).thenReturn(stop);

    Stop saved = stopService.save(stop);

    assertEquals(address, saved.getAddress());
    verify(addressRepository).save(address);
    verify(stopRepository).save(stop);
  }

  @Test
  void save_AllowsNullAddress() {
    Stop stop = new Stop();
    stop.setAddress(null);

    when(stopRepository.save(stop)).thenReturn(stop);

    Stop saved = stopService.save(stop);

    assertNull(saved.getAddress());
    verify(stopRepository).save(stop);
    verifyNoInteractions(addressRepository);
  }

  @Test
  void deleteById_DeletesWhenExists() {
    when(stopRepository.existsById(1)).thenReturn(true);

    boolean result = stopService.deleteById(1);

    assertTrue(result);
    verify(stopRepository).deleteById(1);
  }

  @Test
  void deleteById_ReturnsFalseWhenNotExists() {
    when(stopRepository.existsById(1)).thenReturn(false);

    boolean result = stopService.deleteById(1);

    assertFalse(result);
    verify(stopRepository, never()).deleteById(anyInt());
  }

  @Test
  void update_UpdatesStopAndAddress() {
    Stop existingStop = new Stop();
    existingStop.setStopName("Old Stop");
    existingStop.setAddress(createAddress("OldStreet"));

    Stop updatedStop = new Stop();
    updatedStop.setStopName("New Stop");
    updatedStop.setAddress(createAddress("NewStreet"));

    when(stopRepository.findById(1)).thenReturn(Optional.of(existingStop));
    when(addressRepository.save(any(Address.class))).thenAnswer(
        i -> i.getArgument(0));
    when(stopRepository.save(existingStop)).thenReturn(existingStop);

    Optional<Stop> result = stopService.update(1, updatedStop);

    assertTrue(result.isPresent());
    assertEquals("New Stop", result.get().getStopName());
    assertEquals("NewStreet", result.get().getAddress().getStreet());
    verify(addressRepository).save(existingStop.getAddress());
    verify(stopRepository).save(existingStop);
  }

  @Test
  void update_UpdatesStopWithoutAddressChange() {
    Address address = createAddress();
    Stop existingStop = new Stop();
    existingStop.setStopName("Old Stop");
    existingStop.setAddress(address);

    Stop updatedStop = new Stop();
    updatedStop.setStopName("New Stop");
    updatedStop.setAddress(createAddress());

    when(stopRepository.findById(1)).thenReturn(Optional.of(existingStop));
    when(stopRepository.save(existingStop)).thenReturn(existingStop);

    Optional<Stop> result = stopService.update(1, updatedStop);

    assertTrue(result.isPresent());
    assertEquals("New Stop", result.get().getStopName());
    verify(addressRepository, never()).save(any());
    verify(stopRepository).save(existingStop);
  }

  @Test
  void update_ReturnsEmptyWhenNotFound() {
    Stop updatedStop = new Stop();
    when(stopRepository.findById(1)).thenReturn(Optional.empty());

    Optional<Stop> result = stopService.update(1, updatedStop);

    assertFalse(result.isPresent());
    verify(stopRepository, never()).save(any());
  }

  private Address createAddress() {
    return createAddress("MainStreet");
  }

  private Address createAddress(String street) {
    Address address = new Address();
    address.setStreet(street);
    address.setCity("City");
    address.setPostalCode("12345");
    address.setBuildingNumber("10");
    address.setApartmentNumber("2A");
    return address;
  }
}
