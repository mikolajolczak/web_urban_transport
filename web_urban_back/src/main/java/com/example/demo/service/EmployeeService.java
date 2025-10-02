package com.example.demo.service;

import com.example.demo.entity.Address;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Office;
import com.example.demo.entity.Position;
import com.example.demo.entity.Salary;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.OfficeRepository;
import com.example.demo.repository.PositionRepository;
import com.example.demo.repository.SalaryRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
  private final EmployeeRepository employeeRepository;
  private final AddressRepository addressRepository;
  private final OfficeRepository officeRepository;
  private final SalaryRepository salaryRepository;
  private final PositionRepository positionRepository;

  public EmployeeService(EmployeeRepository employeeRepository,
                         AddressRepository addressRepository,
                         OfficeRepository officeRepository,
                         SalaryRepository salaryRepository,
                         PositionRepository positionRepository) {
    this.employeeRepository = employeeRepository;
    this.addressRepository = addressRepository;
    this.officeRepository = officeRepository;
    this.salaryRepository = salaryRepository;
    this.positionRepository = positionRepository;
  }

  public List<Employee> findAll() {
    return employeeRepository.findAll();
  }

  public Optional<Employee> findById(int id) {
    return employeeRepository.findById(id);
  }

  public Employee save(Employee employee) {
    Address existingAddress = findOrCreateAddress(employee.getAddress());
    employee.setAddress(existingAddress);
    Office managedOffice =
        officeRepository.findById(employee.getOffice().getId())
            .orElseThrow(() -> new RuntimeException("Office not found"));
    employee.setOffice(managedOffice);
    Salary managedSalary =
        salaryRepository.findById(employee.getSalary().getId())
            .orElseThrow(() -> new RuntimeException("Salary not found"));
    employee.setSalary(managedSalary);
    Position
        managedPosition =
        positionRepository.findById(employee.getPosition().getId())
            .orElseThrow(() -> new RuntimeException("Position not found"));
    employee.setPosition(managedPosition);
    return employeeRepository.save(employee);
  }

  public Optional<Employee> update(int id, Employee employee) {
    return employeeRepository.findById(id).map((existing) -> {
      existing.setFirstName(employee.getFirstName());
      existing.setLastName(employee.getLastName());
      existing.setAccountNumber(employee.getAccountNumber());
      existing.setEmploymentDate(employee.getEmploymentDate());
      existing.setGender(employee.getGender());
      existing.setPersonalIdentificationNumber(
          employee.getPersonalIdentificationNumber());

      Address existingAddress = existing.getAddress();
      Address updatedAddress = employee.getAddress();

      if (!areAddressesEqual(existingAddress, updatedAddress)) {
        existingAddress.setStreet(updatedAddress.getStreet());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setBuildingNumber(updatedAddress.getBuildingNumber());
        existingAddress.setApartmentNumber(updatedAddress.getApartmentNumber());
        existingAddress.setPostalCode(updatedAddress.getPostalCode());

        addressRepository.save(existingAddress);
      }

      Office managedOffice =
          officeRepository.findById(employee.getOffice().getId())
              .orElseThrow(() -> new RuntimeException("Office not found"));
      existing.setOffice(managedOffice);
      Salary managedSalary =
          salaryRepository.findById(employee.getSalary().getId())
              .orElseThrow(() -> new RuntimeException("Salary not found"));
      existing.setSalary(managedSalary);
      Position
          managedPosition =
          positionRepository.findById(employee.getPosition().getId())
              .orElseThrow(() -> new RuntimeException("Position not found"));
      existing.setPosition(managedPosition);
      return employeeRepository.save(existing);
    });
  }

  public boolean deleteById(int id) {
    if (employeeRepository.existsById(id)) {
      employeeRepository.deleteById(id);
      return true;
    }
    return false;
  }

  public Address findOrCreateAddress(Address address) {
    if (address == null) {
      return null;
    }

    Optional<Address> existingAddress =
        addressRepository.findByStreetAndCityAndPostalCodeAndBuildingNumberAndApartmentNumber(
            address.getStreet(),
            address.getCity(),
            address.getPostalCode(),
            address.getBuildingNumber(),
            address.getApartmentNumber()
        );

    return existingAddress.orElseGet(() -> addressRepository.save(address));
  }

  public boolean areAddressesEqual(Address a, Address b) {
    if (a == b) {
      return true;
    }
    if (a == null || b == null) {
      return false;
    }
    return Objects.equals(a.getStreet(), b.getStreet()) &&
        Objects.equals(a.getCity(), b.getCity()) &&
        Objects.equals(a.getBuildingNumber(), b.getBuildingNumber()) &&
        Objects.equals(a.getApartmentNumber(), b.getApartmentNumber()) &&
        Objects.equals(a.getPostalCode(), b.getPostalCode());
  }
}
