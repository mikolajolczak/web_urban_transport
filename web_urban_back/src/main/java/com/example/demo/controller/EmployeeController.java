package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
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
 * REST controller for managing {@link Employee} entities.
 *
 * <p>Provides endpoints for CRUD operations including:
 * <ul>
 *     <li>Retrieve all employees</li>
 *     <li>Retrieve an employee by ID</li>
 *     <li>Create a new employee</li>
 *     <li>Update an existing employee</li>
 *     <li>Delete an employee by ID</li>
 * </ul>
 * </p>
 */
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
  /**
   * Service used to manage {@link Employee} entities.
   *
   * <p>This service provides CRUD operations for employees and is injected via
   * constructor.
   * It should not be {@code null}.
   * </p>
   */
  private final EmployeeService employeeService;

  /**
   * Constructs a new {@code EmployeeController} with the provided
   * {@link EmployeeService}.
   *
   * @param employeeServiceParam the service used to manage employee data
   */
  public EmployeeController(final EmployeeService employeeServiceParam) {
    this.employeeService = employeeServiceParam;
  }

  /**
   * Retrieves all employees.
   *
   * @return {@link ResponseEntity} containing the list of all
   *     {@link Employee} objects
   */
  @GetMapping
  public ResponseEntity<List<Employee>> findAll() {
    return ResponseEntity.ok(employeeService.findAll());
  }

  /**
   * Retrieves an employee by their unique ID.
   *
   * @param id the ID of the employee to retrieve
   * @return {@link ResponseEntity} containing the {@link Employee} if found,
   *     or {@code 404 Not Found} if no employee exists with the given ID
   */
  @GetMapping("/{id}")
  public ResponseEntity<Employee> findById(@PathVariable final int id) {
    return employeeService.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Creates a new employee.
   *
   * @param employee the {@link Employee} object to create
   * @return {@link ResponseEntity} containing the created {@link Employee}
   */
  @PostMapping
  public ResponseEntity<Employee> save(@RequestBody final Employee employee) {
    return ResponseEntity.ok(employeeService.save(employee));
  }

  /**
   * Updates an existing employee identified by their ID.
   *
   * @param id       the ID of the employee to update
   * @param employee the updated {@link Employee} data
   * @return {@link ResponseEntity} containing the updated {@link Employee}
   *     if the update is successful,
   *     or {@code 404 Not Found} if no employee exists with the given ID
   */
  @PutMapping("/{id}")
  public ResponseEntity<Employee> update(@PathVariable final int id,
                                         @RequestBody final Employee employee) {
    return employeeService.update(id, employee)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Deletes an employee by their unique ID.
   *
   * @param id the ID of the employee to delete
   * @return {@link ResponseEntity} with {@code 200 OK} if deletion is
   *     successful,
   *     or {@code 404 Not Found} if no employee exists with the given ID
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Employee> delete(@PathVariable final int id) {
    return employeeService.deleteById(id)
        ? ResponseEntity.ok().build()
        : ResponseEntity.notFound().build();
  }
}
