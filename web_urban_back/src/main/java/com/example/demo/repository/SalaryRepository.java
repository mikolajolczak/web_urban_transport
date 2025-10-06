package com.example.demo.repository;

import com.example.demo.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Salary} entities.
 */
@Repository
public interface SalaryRepository extends JpaRepository<Salary, Integer> {
}
