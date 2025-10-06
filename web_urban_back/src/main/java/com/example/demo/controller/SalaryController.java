package com.example.demo.controller;

import com.example.demo.entity.Salary;
import com.example.demo.service.SalaryService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing salary data.
 *
 * <p>This controller provides endpoints for retrieving salary information.
 * </p>
 */
@RestController
@RequestMapping("/api/salary")
public class SalaryController {

  /**
   * Service for handling salary-related business logic.
   */
  private final SalaryService salaryService;

  /**
   * Constructs a new {@code SalaryController} with the specified salary
   * service.
   *
   * @param salaryServiceParam the salary service to be injected
   */
  public SalaryController(final SalaryService salaryServiceParam) {
    this.salaryService = salaryServiceParam;
  }

  /**
   * Retrieves all salary records.
   *
   * @return a {@link ResponseEntity} containing the list of all
   *     {@link Salary} entities
   */
  @GetMapping
  public ResponseEntity<List<Salary>> findAll() {
    return ResponseEntity.ok(salaryService.findAll());
  }
}
