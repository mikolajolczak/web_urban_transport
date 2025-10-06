package com.example.demo.controller;

import com.example.demo.entity.Position;
import com.example.demo.service.PositionService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PositionControllerTest {

  @Mock
  private PositionService positionService;

  @InjectMocks
  private PositionController positionController;

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
  void findAll_returnsListOfPositions() {
    Position position1 = new Position();
    position1.setId(1);
    position1.setPositionName("Manager");

    Position position2 = new Position();
    position2.setId(2);
    position2.setPositionName("Developer");

    List<Position> positions = Arrays.asList(position1, position2);

    when(positionService.findAll()).thenReturn(positions);

    ResponseEntity<List<Position>> response = positionController.findAll();

    assertEquals(200, response.getStatusCode().value());
    Assertions.assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());
    assertEquals("Manager", response.getBody().get(0).getPositionName());
    assertEquals("Developer", response.getBody().get(1).getPositionName());
  }

  @Test
  void findAll_returnsEmptyList() {
    when(positionService.findAll()).thenReturn(List.of());

    ResponseEntity<List<Position>> response = positionController.findAll();

    assertEquals(200, response.getStatusCode().value());
    Assertions.assertNotNull(response.getBody());
    assertEquals(0, response.getBody().size());
  }
}
