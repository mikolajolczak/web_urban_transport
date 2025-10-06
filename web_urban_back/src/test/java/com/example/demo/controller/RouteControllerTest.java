package com.example.demo.controller;

import com.example.demo.entity.Route;
import com.example.demo.service.RouteService;
import java.util.Arrays;
import java.util.Collections;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RouteControllerTest {

  @Mock
  private RouteService routeService;

  @InjectMocks
  private RouteController routeController;

  private Route route1;
  private Route route2;

  private AutoCloseable mocks;

  @BeforeEach
  void setUp() {
    mocks = MockitoAnnotations.openMocks(this);
    route1 = new Route();
    route1.setId(1);
    route1.setRouteName("Route 1");
    route2 = new Route();
    route2.setId(2);
    route2.setRouteName("Route 2");
  }

  @AfterEach
  void tearDown() throws Exception {
    mocks.close();
  }

  @Test
  void testFindAllReturnsRoutes() {
    List<Route> routes = Arrays.asList(route1, route2);
    when(routeService.findAll()).thenReturn(routes);

    ResponseEntity<List<Route>> response = routeController.findAll();

    assertEquals(200, response.getStatusCode().value());
    Assertions.assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());
    assertEquals("Route 1", response.getBody().get(0).getRouteName());
    assertEquals("Route 2", response.getBody().get(1).getRouteName());
    verify(routeService, times(1)).findAll();
  }

  @Test
  void testFindAllReturnsEmptyList() {
    when(routeService.findAll()).thenReturn(Collections.emptyList());

    ResponseEntity<List<Route>> response = routeController.findAll();

    assertEquals(200, response.getStatusCode().value());
    Assertions.assertNotNull(response.getBody());
    assertEquals(0, response.getBody().size());
    verify(routeService, times(1)).findAll();
  }
}
