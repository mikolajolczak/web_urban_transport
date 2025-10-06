package com.example.demo.controller;

import com.example.demo.entity.Route;
import com.example.demo.entity.RouteStop;
import com.example.demo.entity.Stop;
import com.example.demo.service.RouteStopService;
import java.sql.Date;
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
import static org.mockito.Mockito.when;

class RouteStopControllerTest {

  @Mock
  private RouteStopService routeStopService;

  @InjectMocks
  private RouteStopController routeStopController;

  private RouteStop routeStop1;
  private RouteStop routeStop2;

  private AutoCloseable mocks;

  @BeforeEach
  void setUp() {
    mocks = MockitoAnnotations.openMocks(this);

    Route route = new Route();
    route.setId(1);
    route.setRouteName("Route 1");

    Stop stop1 = new Stop();
    stop1.setId(1);
    stop1.setStopName("Stop A");

    Stop stop2 = new Stop();
    stop2.setId(2);
    stop2.setStopName("Stop B");

    routeStop1 = new RouteStop();
    routeStop1.setId(1);
    routeStop1.setRoute(route);
    routeStop1.setStop(stop1);
    routeStop1.setArrivalTime(Date.valueOf("2025-10-02"));

    routeStop2 = new RouteStop();
    routeStop2.setId(2);
    routeStop2.setRoute(route);
    routeStop2.setStop(stop2);
    routeStop2.setArrivalTime(Date.valueOf("2025-10-03"));
  }

  @AfterEach
  void tearDown() throws Exception {
    mocks.close();
  }

  @Test
  void findAllRouteStops_returnsListOfRouteStops() {
    List<RouteStop> routeStops = Arrays.asList(routeStop1, routeStop2);
    when(routeStopService.findAll()).thenReturn(routeStops);

    ResponseEntity<List<RouteStop>> response =
        routeStopController.findAllRouteStops();

    assertEquals(200, response.getStatusCode().value());
    Assertions.assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());

    RouteStop first = response.getBody().get(0);
    assertEquals(1, first.getId());
    assertEquals("Route 1", first.getRoute().getRouteName());
    assertEquals("Stop A", first.getStop().getStopName());
    assertEquals(Date.valueOf("2025-10-02"), first.getArrivalTime());

    RouteStop second = response.getBody().get(1);
    assertEquals(2, second.getId());
    assertEquals("Route 1", second.getRoute().getRouteName());
    assertEquals("Stop B", second.getStop().getStopName());
    assertEquals(Date.valueOf("2025-10-03"), second.getArrivalTime());
  }

  @Test
  void findAllRouteStops_returnsEmptyList() {
    when(routeStopService.findAll()).thenReturn(Collections.emptyList());

    ResponseEntity<List<RouteStop>> response =
        routeStopController.findAllRouteStops();

    assertEquals(200, response.getStatusCode().value());
    Assertions.assertNotNull(response.getBody());
    assertEquals(0, response.getBody().size());
  }
}
