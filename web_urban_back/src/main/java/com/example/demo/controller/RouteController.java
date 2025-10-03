package com.example.demo.controller;

import com.example.demo.entity.Route;
import com.example.demo.service.RouteService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link Route} entities.
 *
 * <p>This controller provides endpoints to retrieve route data.
 * </p>
 */
@RestController
@RequestMapping("/api/route")
public class RouteController {

  /**
   * Service layer dependency for {@link Route} management.
   */
  private final RouteService routeService;

  /**
   * Constructs a {@code RouteController} with the required
   * {@link RouteService}.
   *
   * @param routeServiceParam the service to handle route operations, must
   *                          not be {@code null}
   */
  public RouteController(final RouteService routeServiceParam) {
    this.routeService = routeServiceParam;
  }

  /**
   * Retrieves all available routes.
   *
   * <p>This endpoint returns a {@link List} of {@link Route} entities
   * wrapped in a {@link ResponseEntity} with HTTP status 200 (OK).
   * </p>
   *
   * @return a {@link ResponseEntity} containing the list of all routes
   */
  @GetMapping
  public ResponseEntity<List<Route>> findAll() {
    return ResponseEntity.ok(routeService.findAll());
  }
}
