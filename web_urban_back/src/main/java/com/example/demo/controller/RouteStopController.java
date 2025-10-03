package com.example.demo.controller;

import com.example.demo.entity.RouteStop;
import com.example.demo.service.RouteStopService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller responsible for handling requests related to
 * {@link RouteStop}.
 *
 * <p>This controller provides endpoints to retrieve information about route
 * stops.
 * </p>
 */
@RestController
@RequestMapping("/api/route_stop")
public class RouteStopController {

  /**
   * Service layer dependency for route stop operations.
   */
  private final RouteStopService routeStopService;

  /**
   * Constructs a new {@code RouteStopController} with the provided service.
   *
   * @param routeStopServiceParam the service to manage route stops
   */
  public RouteStopController(final RouteStopService routeStopServiceParam) {
    this.routeStopService = routeStopServiceParam;
  }

  /**
   * Retrieves all available route stops.
   *
   * <p>This method returns a list of all {@link RouteStop} entities present in
   * the system.
   * </p>
   *
   * @return a {@link ResponseEntity} containing a list of all route stops
   */
  @GetMapping
  public ResponseEntity<List<RouteStop>> findAllRouteStops() {
    return ResponseEntity.ok(routeStopService.findAll());
  }
}
