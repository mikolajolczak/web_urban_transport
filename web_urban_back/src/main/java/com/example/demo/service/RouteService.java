package com.example.demo.service;

import com.example.demo.entity.Route;
import com.example.demo.repository.RouteRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RouteService {
  private final RouteRepository routeRepository;

  public RouteService(RouteRepository routeRepository) {
    this.routeRepository = routeRepository;
  }

  public List<Route> findAll() {
    return routeRepository.findAll();
  }
}
