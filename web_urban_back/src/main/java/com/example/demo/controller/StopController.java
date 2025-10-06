package com.example.demo.controller;

import com.example.demo.entity.Stop;
import com.example.demo.service.StopService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link Stop} entities.
 *
 * <p>Provides CRUD operations for stops via HTTP endpoints.
 * </p>
 */
@RestController
@RequestMapping("/api/stop")
public class StopController {
  /**
   * Service layer for managing {@link Stop} entities.
   *
   * <p>Used internally by the controller to perform CRUD operations.
   * </p>
   */
  private final StopService stopService;

  /**
   * Constructs a new {@code StopController} with the given {@link StopService}.
   *
   * @param stopServiceParam the service used to manage stops
   */
  public StopController(final StopService stopServiceParam) {
    this.stopService = stopServiceParam;
  }

  /**
   * Retrieves all stops.
   *
   * @return {@link ResponseEntity} containing a list of all stops
   */
  @GetMapping
  public ResponseEntity<List<Stop>> getStops() {
    return ResponseEntity.ok(stopService.findAll());
  }

  /**
   * Creates a new stop.
   *
   * @param stop the stop to create
   * @return {@link ResponseEntity} containing the created stop
   */
  @PostMapping
  public ResponseEntity<Stop> saveStop(@RequestBody final Stop stop) {
    return ResponseEntity.ok(stopService.save(stop));
  }

  /**
   * Deletes a stop by its ID.
   *
   * @param id the ID of the stop to delete
   * @return {@link ResponseEntity#ok()} if deleted,
   *     {@link ResponseEntity#notFound()} otherwise
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Stop> deleteStop(@PathVariable final int id) {
    return stopService.deleteById(id) ? ResponseEntity.ok().build()
        : ResponseEntity.notFound().build();
  }

  /**
   * Retrieves a stop by its ID.
   *
   * @param id the ID of the stop to retrieve
   * @return {@link ResponseEntity} containing the stop if found,
   *     {@link ResponseEntity#notFound()} otherwise
   */
  @GetMapping("/{id}")
  public ResponseEntity<Stop> getStopById(@PathVariable final int id) {
    return stopService.findById(id).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Updates a stop by its ID.
   *
   * @param id   the ID of the stop to update
   * @param stop the updated stop data
   * @return {@link ResponseEntity} containing the updated stop if successful,
   *     {@link ResponseEntity#notFound()} if the stop does not exist
   */
  @PutMapping("/{id}")
  public ResponseEntity<Stop> updateStop(@RequestBody final Stop stop,
                                         @PathVariable final int id) {
    return stopService.update(id, stop).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
