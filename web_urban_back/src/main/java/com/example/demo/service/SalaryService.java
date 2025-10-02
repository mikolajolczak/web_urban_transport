package com.example.demo.service;

import com.example.demo.entity.Salary;
import com.example.demo.repository.SalaryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {
  private final SalaryRepository salaryRepository;

  public SalaryService(SalaryRepository salaryRepository) {
    this.salaryRepository = salaryRepository;
  }

  public List<Salary> findAll() {
    return salaryRepository.findAll();
  }
}
