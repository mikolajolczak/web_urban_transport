package com.example.demo.service;

import com.example.demo.entity.Route;
import com.example.demo.repository.RouteRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RouteServiceTest {

  private RouteRepository routeRepository;
  private RouteService routeService;

  @BeforeEach
  void setUp() {
    routeRepository = Mockito.mock(RouteRepository.class);
    routeService = new RouteService(routeRepository);
  }

  @Test
  void shouldReturnAllRoutesWhenRepositoryReturnsMultipleRoutes() {
    Route route1 = new Route();
    Route route2 = new Route();
    List<Route> expectedRoutes = Arrays.asList(route1, route2);

    when(routeRepository.findAll()).thenReturn(expectedRoutes);

    List<Route> actualRoutes = routeService.findAll();

    assertEquals(expectedRoutes, actualRoutes);
    verify(routeRepository, times(1)).findAll();
  }

  @Test
  void shouldReturnEmptyListWhenRepositoryReturnsNoRoutes() {
    when(routeRepository.findAll()).thenReturn(Collections.emptyList());

    List<Route> actualRoutes = routeService.findAll();

    assertEquals(Collections.emptyList(), actualRoutes);
    verify(routeRepository, times(1)).findAll();
  }

  @Test
  void shouldPropagateExceptionWhenRepositoryThrowsException() {
    RuntimeException exception = new RuntimeException("Database error");
    when(routeRepository.findAll()).thenThrow(exception);

    RuntimeException thrown = org.junit.jupiter.api.Assertions.assertThrows(
        RuntimeException.class,
        () -> routeService.findAll()
    );

    assertEquals("Database error", thrown.getMessage());
    verify(routeRepository, times(1)).findAll();
  }
}
