package com.example.demo.controller;

import com.example.demo.entity.Route;
import com.example.demo.service.RouteService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/route")
public class RouteController {
  private final RouteService routeService;

  public RouteController(RouteService routeService) {
    this.routeService = routeService;
  }

  @GetMapping
  public ResponseEntity<List<Route>> findAll() {
    return ResponseEntity.ok(routeService.findAll());
  }
}
