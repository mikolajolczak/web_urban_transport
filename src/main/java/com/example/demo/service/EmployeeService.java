package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
  private final EmployeeRepository employeeRepository;

  public EmployeeService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public List<Employee> findAll() {
    return employeeRepository.findAll();
  }

  public Optional<Employee> findById(int id) {
    return employeeRepository.findById(id);
  }

  public Employee save(Employee employee) {
    return employeeRepository.save(employee);
  }

  public Optional<Employee> update(int id, Employee employee) {
    return employeeRepository.findById(id).map((existing) -> {
      existing.setAddress(employee.getAddress());
      existing.setFirstName(employee.getFirstName());
      existing.setLastName(employee.getLastName());
      existing.setAccountNumber(employee.getAccountNumber());
      existing.setSalary(employee.getSalary());
      existing.setEmploymentDate(employee.getEmploymentDate());
      existing.setGender(employee.getGender());
      existing.setOffice(employee.getOffice());
      existing.setPosition(employee.getPosition());
      existing.setPersonalIdentificationNumber(
          employee.getPersonalIdentificationNumber());
      return existing;
    });
  }

  public boolean deleteById(int id) {
    if (employeeRepository.existsById(id)) {
      employeeRepository.deleteById(id);
      return true;
    }
    return false;
  }


}
