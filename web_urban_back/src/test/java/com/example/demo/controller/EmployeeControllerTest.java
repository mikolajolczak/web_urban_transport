package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeControllerTest {

  @Mock
  private EmployeeService employeeService;

  @InjectMocks
  private EmployeeController employeeController;

  private Employee employee1;
  private Employee employee2;

  private AutoCloseable mocks;

  @BeforeEach
  void setUp() {
    mocks = MockitoAnnotations.openMocks(this);
    employee1 = new Employee();
    employee1.setId(1);
    employee1.setFirstName("John");
    employee1.setLastName("Doe");

    employee2 = new Employee();
    employee2.setId(2);
    employee2.setFirstName("Jane");
    employee2.setLastName("Smith");
  }

  @AfterEach
  void tearDown() throws Exception {
    mocks.close();
  }

  @Test
  void testFindAll() {
    List<Employee> employees = Arrays.asList(employee1, employee2);
    when(employeeService.findAll()).thenReturn(employees);

    ResponseEntity<List<Employee>> response = employeeController.findAll();

    Assertions.assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());
    assertEquals(employee1, response.getBody().get(0));
    verify(employeeService, times(1)).findAll();
  }

  @Test
  void testFindById_Found() {
    when(employeeService.findById(1)).thenReturn(Optional.of(employee1));

    ResponseEntity<Employee> response = employeeController.findById(1);

    assertNotNull(response.getBody());
    assertEquals(employee1, response.getBody());
    verify(employeeService, times(1)).findById(1);
  }

  @Test
  void testFindById_NotFound() {
    when(employeeService.findById(3)).thenReturn(Optional.empty());

    ResponseEntity<Employee> response = employeeController.findById(3);

    assertEquals(404, response.getStatusCode().value());
    verify(employeeService, times(1)).findById(3);
  }

  @Test
  void testSave() {
    when(employeeService.save(employee1)).thenReturn(employee1);

    ResponseEntity<Employee> response = employeeController.save(employee1);

    assertEquals(employee1, response.getBody());
    verify(employeeService, times(1)).save(employee1);
  }

  @Test
  void testUpdate_Found() {
    Employee updatedEmployee = new Employee();
    updatedEmployee.setId(1);
    updatedEmployee.setFirstName("John Updated");

    when(employeeService.update(1, updatedEmployee)).thenReturn(
        Optional.of(updatedEmployee));

    ResponseEntity<Employee> response =
        employeeController.update(1, updatedEmployee);

    assertEquals(updatedEmployee, response.getBody());
    verify(employeeService, times(1)).update(1, updatedEmployee);
  }

  @Test
  void testUpdate_NotFound() {
    Employee updatedEmployee = new Employee();
    updatedEmployee.setId(3);
    updatedEmployee.setFirstName("Nonexistent");

    when(employeeService.update(3, updatedEmployee)).thenReturn(
        Optional.empty());

    ResponseEntity<Employee> response =
        employeeController.update(3, updatedEmployee);

    assertEquals(404, response.getStatusCode().value());
    verify(employeeService, times(1)).update(3, updatedEmployee);
  }

  @Test
  void testDelete_Found() {
    when(employeeService.deleteById(1)).thenReturn(true);

    ResponseEntity<Employee> response = employeeController.delete(1);

    assertEquals(200, response.getStatusCode().value());
    verify(employeeService, times(1)).deleteById(1);
  }

  @Test
  void testDelete_NotFound() {
    when(employeeService.deleteById(3)).thenReturn(false);

    ResponseEntity<Employee> response = employeeController.delete(3);

    assertEquals(404, response.getStatusCode().value());
    verify(employeeService, times(1)).deleteById(3);
  }
}
