package com.example.demo.repository;

import com.example.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Employee} entities.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
