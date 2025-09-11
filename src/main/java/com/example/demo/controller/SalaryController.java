package com.example.demo.controller;

import com.example.demo.entity.Salary;
import com.example.demo.service.SalaryService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {
  private final SalaryService salaryService;

  public SalaryController(SalaryService salaryService) {
    this.salaryService = salaryService;
  }

  @GetMapping
  public ResponseEntity<List<Salary>> findAll() {
    return ResponseEntity.ok(salaryService.findAll());
  }
}
