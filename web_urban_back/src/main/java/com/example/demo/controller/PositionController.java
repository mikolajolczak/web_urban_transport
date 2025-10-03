package com.example.demo.controller;

import com.example.demo.entity.Position;
import com.example.demo.service.PositionService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link Position} entities.
 *
 * <p>Provides endpoints to retrieve position data.
 * </p>
 */
@RestController
@RequestMapping("/api/position")
public class PositionController {

  /**
   * Service layer for {@link Position} operations.
   */
  private final PositionService positionService;

  /**
   * Constructs a new {@code PositionController} with the specified
   * {@link PositionService}.
   *
   * @param positionServiceParam the service used to manage positions
   */
  public PositionController(final PositionService positionServiceParam) {
    this.positionService = positionServiceParam;
  }

  /**
   * Retrieves all positions.
   *
   * @return a {@link ResponseEntity} containing a list of all
   *     {@link Position} entities
   */
  @GetMapping
  public ResponseEntity<List<Position>> findAll() {
    return ResponseEntity.ok(positionService.findAll());
  }
}
