package com.example.demo.controller;

import com.example.demo.entity.Office;
import com.example.demo.service.OfficeService;
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
 * REST controller for managing {@link Office} entities.
 *
 * <p>Provides endpoints to perform CRUD operations on offices.
 * </p>
 */
@RestController
@RequestMapping("/api/office")
public class OfficeController {
  /**
   * The service layer responsible for handling business logic related to
   * {@link Office} entities.
   */
  private final OfficeService officeService;

  /**
   * Constructs an {@code OfficeController} with the given
   * {@link OfficeService}.
   *
   * @param officeServiceParam the service to handle office-related business
   *                           logic
   */
  public OfficeController(final OfficeService officeServiceParam) {
    this.officeService = officeServiceParam;
  }

  /**
   * Retrieves all offices.
   *
   * @return a {@link ResponseEntity} containing the list of all offices with
   *     HTTP status 200 OK
   */
  @GetMapping
  public ResponseEntity<List<Office>> findAll() {
    return ResponseEntity.ok(officeService.findAll());
  }

  /**
   * Deletes an office by its ID.
   *
   * @param id the ID of the office to delete
   * @return {@link ResponseEntity#ok()} if the office was deleted, or
   *     {@link ResponseEntity#notFound()} if the office does not exist
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOffice(@PathVariable final int id) {
    return officeService.deleteById(id) ? ResponseEntity.ok().build()
        : ResponseEntity.notFound().build();
  }

  /**
   * Retrieves an office by its ID.
   *
   * @param id the ID of the office to retrieve
   * @return a {@link ResponseEntity} containing the office if found,
   *     or {@link ResponseEntity#notFound()} if no office exists with the
   *     given ID
   */
  @GetMapping("/{id}")
  public ResponseEntity<Office> findById(@PathVariable final int id) {
    return officeService.findById(id).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Updates an existing office.
   *
   * @param id            the ID of the office to update
   * @param updatedOffice the updated office details
   * @return a {@link ResponseEntity} containing the updated office if the
   *     update was successful,
   *     or {@link ResponseEntity#notFound()} if no office exists with the
   *     given ID
   */
  @PutMapping("/{id}")
  public ResponseEntity<Office> updateOffice(@PathVariable final int id,
                                             @RequestBody
                                             final Office updatedOffice) {
    return officeService.updateOffice(id, updatedOffice)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Creates a new office.
   *
   * @param office the office details to save
   * @return a {@link ResponseEntity} containing the saved office with HTTP
   *     status 200 OK
   */
  @PostMapping
  public ResponseEntity<Office> saveOffice(@RequestBody final Office office) {
    return ResponseEntity.ok(officeService.save(office));
  }
}
