package com.example.demo.controller;

import com.example.demo.entity.Salary;
import com.example.demo.service.SalaryService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SalaryControllerTest {

  @Mock
  private SalaryService salaryService;

  @InjectMocks
  private SalaryController salaryController;

  private AutoCloseable mocks;

  @BeforeEach
  void setUp() {
    mocks = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() throws Exception {
    mocks.close();
  }

  @Test
  void findAllReturnsListOfSalaries() {
    Salary salary1 = new Salary();
    Salary salary2 = new Salary();
    List<Salary> salaries = Arrays.asList(salary1, salary2);

    when(salaryService.findAll()).thenReturn(salaries);

    ResponseEntity<List<Salary>> response = salaryController.findAll();

    assertEquals(200, response.getStatusCode().value());
    assertEquals(salaries, response.getBody());
    verify(salaryService, times(1)).findAll();
  }

  @Test
  void findAllReturnsEmptyList() {
    when(salaryService.findAll()).thenReturn(Collections.emptyList());

    ResponseEntity<List<Salary>> response = salaryController.findAll();

    assertEquals(200, response.getStatusCode().value());
    assertEquals(Collections.emptyList(), response.getBody());
    verify(salaryService, times(1)).findAll();
  }
}
