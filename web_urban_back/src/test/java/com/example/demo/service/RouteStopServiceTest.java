package com.example.demo.service;

import com.example.demo.entity.RouteStop;
import com.example.demo.repository.RouteStopRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RouteStopServiceTest {

  private RouteStopRepository routeStopRepository;
  private RouteStopService routeStopService;

  @BeforeEach
  void setUp() {
    routeStopRepository = mock(RouteStopRepository.class);
    routeStopService = new RouteStopService(routeStopRepository);
  }

  @Test
  @DisplayName(
      "should return all route stops when repository returns a non-empty list")
  void shouldReturnAllRouteStops() {
    RouteStop stop1 = new RouteStop();
    RouteStop stop2 = new RouteStop();
    List<RouteStop> stops = Arrays.asList(stop1, stop2);

    when(routeStopRepository.findAll()).thenReturn(stops);

    List<RouteStop> result = routeStopService.findAll();

    assertThat(result).isNotNull().hasSize(2).containsExactly(stop1, stop2);
    verify(routeStopRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("should return empty list when repository returns empty list")
  void shouldReturnEmptyList() {
    when(routeStopRepository.findAll()).thenReturn(Collections.emptyList());

    List<RouteStop> result = routeStopService.findAll();

    assertThat(result).isNotNull().isEmpty();
    verify(routeStopRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("should propagate exception thrown by repository")
  void shouldPropagateException() {
    when(routeStopRepository.findAll()).thenThrow(
        new RuntimeException("DB error"));

    RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(
        RuntimeException.class,
        () -> routeStopService.findAll()
    );

    assertThat(exception).hasMessage("DB error");
    verify(routeStopRepository, times(1)).findAll();
  }
}
