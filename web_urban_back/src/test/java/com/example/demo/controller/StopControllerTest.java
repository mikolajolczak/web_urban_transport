package com.example.demo.controller;

import com.example.demo.entity.Stop;
import com.example.demo.service.StopService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StopControllerTest {

  @Mock
  private StopService stopService;

  @InjectMocks
  private StopController stopController;

  private Stop stop1;
  private Stop stop2;

  private AutoCloseable mocks;

  @BeforeEach
  void setUp() {
    mocks = MockitoAnnotations.openMocks(this);
    stop1 = new Stop();
    stop1.setId(1);
    stop1.setStopName("Stop 1");

    stop2 = new Stop();
    stop2.setId(2);
    stop2.setStopName("Stop 2");
  }

  @AfterEach
  void tearDown() throws Exception {
    mocks.close();
  }

  @Test
  void getStops_shouldReturnAllStops() {
    when(stopService.findAll()).thenReturn(Arrays.asList(stop1, stop2));
    ResponseEntity<List<Stop>> response = stopController.getStops();
    assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());
    assertTrue(response.getBody().contains(stop1));
    assertTrue(response.getBody().contains(stop2));
    verify(stopService, times(1)).findAll();
  }

  @Test
  void saveStop_shouldReturnSavedStop() {
    when(stopService.save(ArgumentMatchers.any(Stop.class))).thenReturn(stop1);
    ResponseEntity<Stop> response = stopController.saveStop(stop1);
    assertEquals(stop1, response.getBody());
    verify(stopService, times(1)).save(stop1);
  }

  @Test
  void deleteStop_whenStopExists_shouldReturnOk() {
    when(stopService.deleteById(1)).thenReturn(true);
    ResponseEntity<Stop> response = stopController.deleteStop(1);
    assertEquals(200, response.getStatusCode().value());
    verify(stopService, times(1)).deleteById(1);
  }

  @Test
  void deleteStop_whenStopDoesNotExist_shouldReturnNotFound() {
    when(stopService.deleteById(1)).thenReturn(false);
    ResponseEntity<Stop> response = stopController.deleteStop(1);
    assertEquals(404, response.getStatusCode().value());
    verify(stopService, times(1)).deleteById(1);
  }

  @Test
  void getStopById_whenStopExists_shouldReturnStop() {
    when(stopService.findById(1)).thenReturn(Optional.of(stop1));
    ResponseEntity<Stop> response = stopController.getStopById(1);
    assertEquals(stop1, response.getBody());
    assertEquals(200, response.getStatusCode().value());
    verify(stopService, times(1)).findById(1);
  }

  @Test
  void getStopById_whenStopDoesNotExist_shouldReturnNotFound() {
    when(stopService.findById(1)).thenReturn(Optional.empty());
    ResponseEntity<Stop> response = stopController.getStopById(1);
    assertEquals(404, response.getStatusCode().value());
    verify(stopService, times(1)).findById(1);
  }

  @Test
  void updateStop_whenStopExists_shouldReturnUpdatedStop() {
    when(stopService.update(eq(1), any(Stop.class))).thenReturn(
        Optional.of(stop1));
    ResponseEntity<Stop> response = stopController.updateStop(stop1, 1);
    assertEquals(stop1, response.getBody());
    assertEquals(200, response.getStatusCode().value());
    verify(stopService, times(1)).update(1, stop1);
  }

  @Test
  void updateStop_whenStopDoesNotExist_shouldReturnNotFound() {
    when(stopService.update(eq(1), any(Stop.class))).thenReturn(
        Optional.empty());
    ResponseEntity<Stop> response = stopController.updateStop(stop1, 1);
    assertEquals(404, response.getStatusCode().value());
    verify(stopService, times(1)).update(1, stop1);
  }
}
