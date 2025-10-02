package com.example.demo.service;

import com.example.demo.entity.RouteStop;
import com.example.demo.repository.RouteStopRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RouteStopService {
  private final RouteStopRepository routeStopRepository;

  public RouteStopService(RouteStopRepository routeStopRepository) {
    this.routeStopRepository = routeStopRepository;
  }

  public List<RouteStop> findAll() {
    return routeStopRepository.findAll();
  }
}
