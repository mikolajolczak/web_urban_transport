package com.example.demo.controller;

import com.example.demo.entity.RouteStop;
import com.example.demo.service.RouteStopService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/route_stop")
public class RouteStopController {
  private final RouteStopService routeStopService;

  public RouteStopController(RouteStopService routeStopService) {
    this.routeStopService = routeStopService;
  }

  @GetMapping
  public ResponseEntity<List<RouteStop>> findAllRouteStops() {
    return ResponseEntity.ok(routeStopService.findAll());
  }

}
