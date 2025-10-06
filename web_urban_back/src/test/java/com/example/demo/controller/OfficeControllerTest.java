package com.example.demo.controller;

import com.example.demo.entity.Office;
import com.example.demo.service.OfficeService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OfficeControllerTest {

  @Mock
  private OfficeService officeService;

  @InjectMocks
  private OfficeController officeController;

  private Office office1;
  private Office office2;


  private AutoCloseable mocks;

  @BeforeEach
  void setUp() {
    mocks = MockitoAnnotations.openMocks(this);
    office1 = new Office();
    office1.setId(1);
    office1.setOfficeName("Office 1");

    office2 = new Office();
    office2.setId(2);
    office2.setOfficeName("Office 2");
  }

  @AfterEach
  void tearDown() throws Exception {
    mocks.close();
  }

  @Test
  void findAll_ReturnsListOfOffices() {
    List<Office> offices = Arrays.asList(office1, office2);
    when(officeService.findAll()).thenReturn(offices);

    ResponseEntity<List<Office>> response = officeController.findAll();

    assertEquals(200, response.getStatusCode().value());
    Assertions.assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());
    verify(officeService, times(1)).findAll();
  }

  @Test
  void findById_ExistingId_ReturnsOffice() {
    when(officeService.findById(1)).thenReturn(Optional.of(office1));

    ResponseEntity<Office> response = officeController.findById(1);

    assertEquals(200, response.getStatusCode().value());
    assertEquals(office1, response.getBody());
    verify(officeService, times(1)).findById(1);
  }

  @Test
  void findById_NonExistingId_ReturnsNotFound() {
    when(officeService.findById(3)).thenReturn(Optional.empty());

    ResponseEntity<Office> response = officeController.findById(3);

    assertEquals(404, response.getStatusCode().value());
    assertNull(response.getBody());
    verify(officeService, times(1)).findById(3);
  }

  @Test
  void saveOffice_ReturnsSavedOffice() {
    when(officeService.save(office1)).thenReturn(office1);

    ResponseEntity<Office> response = officeController.saveOffice(office1);

    assertEquals(200, response.getStatusCode().value());
    assertEquals(office1, response.getBody());
    verify(officeService, times(1)).save(office1);
  }

  @Test
  void updateOffice_ExistingId_ReturnsUpdatedOffice() {
    Office updatedOffice = new Office();
    updatedOffice.setId(1);
    updatedOffice.setOfficeName("Updated Office");

    when(officeService.updateOffice(1, updatedOffice)).thenReturn(
        Optional.of(updatedOffice));

    ResponseEntity<Office> response =
        officeController.updateOffice(1, updatedOffice);

    assertEquals(200, response.getStatusCode().value());
    assertEquals(updatedOffice, response.getBody());
    verify(officeService, times(1)).updateOffice(1, updatedOffice);
  }

  @Test
  void updateOffice_NonExistingId_ReturnsNotFound() {
    Office updatedOffice = new Office();
    updatedOffice.setId(3);
    updatedOffice.setOfficeName("Updated Office");

    when(officeService.updateOffice(3, updatedOffice)).thenReturn(
        Optional.empty());

    ResponseEntity<Office> response =
        officeController.updateOffice(3, updatedOffice);

    assertEquals(404, response.getStatusCode().value());
    assertNull(response.getBody());
    verify(officeService, times(1)).updateOffice(3, updatedOffice);
  }

  @Test
  void deleteOffice_ExistingId_ReturnsOk() {
    when(officeService.deleteById(1)).thenReturn(true);

    ResponseEntity<?> response = officeController.deleteOffice(1);

    assertEquals(200, response.getStatusCode().value());
    verify(officeService, times(1)).deleteById(1);
  }

  @Test
  void deleteOffice_NonExistingId_ReturnsNotFound() {
    when(officeService.deleteById(3)).thenReturn(false);

    ResponseEntity<?> response = officeController.deleteOffice(3);

    assertEquals(404, response.getStatusCode().value());
    verify(officeService, times(1)).deleteById(3);
  }
}
