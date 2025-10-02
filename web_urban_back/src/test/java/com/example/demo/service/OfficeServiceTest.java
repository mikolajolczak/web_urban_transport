package com.example.demo.service;

import com.example.demo.entity.Address;
import com.example.demo.entity.Office;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.OfficeRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OfficeServiceTest {

  private OfficeRepository officeRepository;
  private AddressRepository addressRepository;
  private OfficeService officeService;

  @BeforeEach
  void setUp() {
    officeRepository = mock(OfficeRepository.class);
    addressRepository = mock(AddressRepository.class);
    officeService = new OfficeService(officeRepository, addressRepository);
  }

  @Test
  void findAll_ShouldReturnAllOffices() {
    Office o1 = new Office();
    Office o2 = new Office();
    when(officeRepository.findAll()).thenReturn(Arrays.asList(o1, o2));

    List<Office> result = officeService.findAll();

    assertThat(result).containsExactly(o1, o2);
    verify(officeRepository).findAll();
  }

  @Test
  void deleteById_WhenExists_ShouldDeleteAndReturnTrue() {
    when(officeRepository.existsById(1)).thenReturn(true);

    boolean result = officeService.deleteById(1);

    assertThat(result).isTrue();
    verify(officeRepository).deleteById(1);
  }

  @Test
  void deleteById_WhenNotExists_ShouldReturnFalse() {
    when(officeRepository.existsById(1)).thenReturn(false);

    boolean result = officeService.deleteById(1);

    assertThat(result).isFalse();
    verify(officeRepository, never()).deleteById(anyInt());
  }

  @Test
  void findById_ShouldReturnOffice() {
    Office office = new Office();
    when(officeRepository.findById(1)).thenReturn(Optional.of(office));

    Optional<Office> result = officeService.findById(1);

    assertThat(result).contains(office);
    verify(officeRepository).findById(1);
  }

  @Test
  void updateOffice_WhenOfficeExistsAndAddressChanges_ShouldUpdateOfficeAndAddress() {
    Address oldAddress = new Address();
    oldAddress.setStreet("Old St");
    oldAddress.setCity("Old City");
    oldAddress.setBuildingNumber("1");
    oldAddress.setApartmentNumber("A");
    oldAddress.setPostalCode("00000");

    Office existingOffice = new Office();
    existingOffice.setId(1);
    existingOffice.setOfficeName("Old Office");
    existingOffice.setAddress(oldAddress);

    Address newAddress = new Address();
    newAddress.setStreet("New St");
    newAddress.setCity("New City");
    newAddress.setBuildingNumber("2");
    newAddress.setApartmentNumber("B");
    newAddress.setPostalCode("11111");

    Office updatedOffice = new Office();
    updatedOffice.setOfficeName("New Office");
    updatedOffice.setAddress(newAddress);

    when(officeRepository.findById(1)).thenReturn(Optional.of(existingOffice));
    when(officeRepository.save(any(Office.class))).thenAnswer(
        inv -> inv.getArgument(0));

    Optional<Office> result = officeService.updateOffice(1, updatedOffice);

    assertThat(result).isPresent();
    assertThat(result.get().getOfficeName()).isEqualTo("New Office");
    assertThat(result.get().getAddress().getStreet()).isEqualTo("New St");
    verify(addressRepository).save(oldAddress);
    verify(officeRepository).save(existingOffice);
  }

  @Test
  void updateOffice_WhenOfficeExistsAndAddressSame_ShouldUpdateOfficeOnly() {
    Address address = new Address();
    address.setStreet("Main St");
    address.setCity("City");
    address.setBuildingNumber("10");
    address.setApartmentNumber("101");
    address.setPostalCode("12345");

    Office existingOffice = new Office();
    existingOffice.setId(1);
    existingOffice.setOfficeName("Old Office");
    existingOffice.setAddress(address);

    Office updatedOffice = new Office();
    updatedOffice.setOfficeName("New Office");
    updatedOffice.setAddress(address);

    when(officeRepository.findById(1)).thenReturn(Optional.of(existingOffice));
    when(officeRepository.save(any(Office.class))).thenAnswer(
        inv -> inv.getArgument(0));

    Optional<Office> result = officeService.updateOffice(1, updatedOffice);

    assertThat(result).isPresent();
    assertThat(result.get().getOfficeName()).isEqualTo("New Office");
    verify(addressRepository, never()).save(any(Address.class));
    verify(officeRepository).save(existingOffice);
  }

  @Test
  void updateOffice_WhenOfficeNotFound_ShouldReturnEmpty() {
    when(officeRepository.findById(1)).thenReturn(Optional.empty());

    Optional<Office> result = officeService.updateOffice(1, new Office());

    assertThat(result).isEmpty();
    verify(officeRepository, never()).save(any(Office.class));
  }

  @Test
  void save_WhenAddressAlreadyExists_ShouldReuseExistingAddress() {
    Address address = new Address();
    address.setStreet("Main St");
    address.setCity("City");
    address.setBuildingNumber("10");
    address.setApartmentNumber("101");
    address.setPostalCode("12345");

    Office office = new Office();
    office.setOfficeName("Office");
    office.setAddress(address);

    when(
        addressRepository.findByStreetAndCityAndPostalCodeAndBuildingNumberAndApartmentNumber(
            anyString(), anyString(), anyString(), anyString(), anyString()))
        .thenReturn(Optional.of(address));
    when(officeRepository.save(any(Office.class))).thenAnswer(
        inv -> inv.getArgument(0));

    Office result = officeService.save(office);

    assertThat(result.getAddress()).isEqualTo(address);
    verify(addressRepository, never()).save(any(Address.class));
    verify(officeRepository).save(office);
  }

  @Test
  void save_WhenAddressNotExists_ShouldSaveNewAddress() {
    Address address = new Address();
    address.setStreet("Main St");
    address.setCity("City");
    address.setBuildingNumber("10");
    address.setApartmentNumber("101");
    address.setPostalCode("12345");

    Office office = new Office();
    office.setOfficeName("Office");
    office.setAddress(address);

    when(
        addressRepository.findByStreetAndCityAndPostalCodeAndBuildingNumberAndApartmentNumber(
            anyString(), anyString(), anyString(), anyString(), anyString()))
        .thenReturn(Optional.empty());
    when(addressRepository.save(any(Address.class))).thenReturn(address);
    when(officeRepository.save(any(Office.class))).thenAnswer(
        inv -> inv.getArgument(0));

    Office result = officeService.save(office);

    assertThat(result.getAddress()).isEqualTo(address);
    verify(addressRepository).save(address);
    verify(officeRepository).save(office);
  }

  @Test
  void save_WhenAddressIsNull_ShouldSaveOfficeWithNullAddress() {
    Office office = new Office();
    office.setOfficeName("Office");
    office.setAddress(null);

    when(officeRepository.save(any(Office.class))).thenAnswer(
        inv -> inv.getArgument(0));

    Office result = officeService.save(office);

    assertThat(result.getAddress()).isNull();
    verify(addressRepository, never()).save(any(Address.class));
    verify(officeRepository).save(office);
  }
}
