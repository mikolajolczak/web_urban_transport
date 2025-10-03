package com.example.demo.service;

import com.example.demo.entity.RouteStop;
import com.example.demo.repository.RouteStopRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service layer responsible for handling operations related to
 * {@link RouteStop}.
 *
 * <p>This class provides methods to retrieve route stop data from the
 * persistence layer.
 * </p>
 */
@Service
public class RouteStopService {

  /**
   * Repository used for accessing {@link RouteStop} data.
   */
  private final RouteStopRepository routeStopRepository;

  /**
   * Constructs a new {@code RouteStopService} with the provided repository.
   *
   * @param routeStopRepositoryParam the repository to be used for accessing
   *                                 route stop data
   */
  public RouteStopService(final RouteStopRepository routeStopRepositoryParam) {
    this.routeStopRepository = routeStopRepositoryParam;
  }

  /**
   * Retrieves all {@link RouteStop} entities from the repository.
   *
   * @return a list of all available route stops
   */
  public List<RouteStop> findAll() {
    return routeStopRepository.findAll();
  }
}
