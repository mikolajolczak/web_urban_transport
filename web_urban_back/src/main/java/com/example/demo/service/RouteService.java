package com.example.demo.service;

import com.example.demo.entity.Route;
import com.example.demo.repository.RouteRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling business logic related to
 * {@link Route} entities.
 *
 * <p>This service provides operations to retrieve {@link Route} data from the
 * underlying repository.
 * </p>
 */
@Service
public class RouteService {

  /**
   * Repository used to perform CRUD operations on {@link Route} entities.
   */
  private final RouteRepository routeRepository;

  /**
   * Constructs a new {@link RouteService} with the specified repository.
   *
   * @param routeRepositoryParam the repository used for {@link Route}
   *                             persistence; must not be {@code null}
   */
  public RouteService(final RouteRepository routeRepositoryParam) {
    this.routeRepository = routeRepositoryParam;
  }

  /**
   * Retrieves all {@link Route} entities from the repository.
   *
   * @return a {@link List} containing all {@link Route} entities; never
   *     {@code null}
   */
  public List<Route> findAll() {
    return routeRepository.findAll();
  }
}
