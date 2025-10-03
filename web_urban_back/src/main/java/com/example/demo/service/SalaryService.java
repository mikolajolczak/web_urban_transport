package com.example.demo.service;

import com.example.demo.entity.Salary;
import com.example.demo.repository.SalaryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling salary-related business logic.
 *
 * <p>This class provides methods for retrieving salary data from the underlying
 * repository.
 * </p>
 */
@Service
public class SalaryService {

  /**
   * Repository used to access and manage {@link Salary} entities.
   */
  private final SalaryRepository salaryRepository;

  /**
   * Constructs a new {@code SalaryService} with the specified repository.
   *
   * @param salaryRepositoryParam the repository used to perform salary data
   *                              operations
   */
  public SalaryService(final SalaryRepository salaryRepositoryParam) {
    this.salaryRepository = salaryRepositoryParam;
  }

  /**
   * Retrieves all salary records from the repository.
   *
   * @return a list containing all {@link Salary} entities
   */
  public List<Salary> findAll() {
    return salaryRepository.findAll();
  }
}
