package com.example.demo.service;

import com.example.demo.entity.Position;
import com.example.demo.repository.PositionRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PositionServiceTest {

  private PositionRepository positionRepository;
  private PositionService positionService;

  @BeforeEach
  void setUp() {
    positionRepository = mock(PositionRepository.class);
    positionService = new PositionService(positionRepository);
  }

  @Test
  void findAllShouldReturnListOfPositions() {
    Position position1 = new Position();
    position1.setId(1);
    position1.setPositionName("Developer");

    Position position2 = new Position();
    position2.setId(2);
    position2.setPositionName("Manager");

    when(positionRepository.findAll()).thenReturn(
        Arrays.asList(position1, position2));

    List<Position> result = positionService.findAll();

    assertThat(result).hasSize(2);
    assertThat(result.get(0).getPositionName()).isEqualTo("Developer");
    assertThat(result.get(1).getPositionName()).isEqualTo("Manager");
    verify(positionRepository, times(1)).findAll();
  }

  @Test
  void findAllShouldReturnEmptyListWhenNoPositionsExist() {
    when(positionRepository.findAll()).thenReturn(Collections.emptyList());

    List<Position> result = positionService.findAll();

    assertThat(result).isEmpty();
    verify(positionRepository, times(1)).findAll();
  }

  @Test
  void constructorShouldAssignRepository() {
    ArgumentCaptor<PositionRepository> ignored =
        ArgumentCaptor.forClass(PositionRepository.class);
    PositionService service = new PositionService(positionRepository);

    assertThat(service).isNotNull();
  }
}
