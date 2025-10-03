package com.example.demo.service;

import static com.example.demo.service.AddressService.updateAddressIfChanged;

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
import java.util.Optional;
import org.springframework.stereotype.Service;


/**
 * Service class for managing {@link Employee} entities.
 *
 * <p>Provides methods for retrieving, creating, updating, and deleting
 * employees.
 * Handles associations with {@link Address}, {@link Office}, {@link Salary},
 * and {@link Position}.
 * </p>
 */
@Service
public class EmployeeService {

  /**
   * Repository for Employee entities.
   */
  private final EmployeeRepository employeeRepository;

  /**
   * Repository for Address entities.
   */
  private final AddressRepository addressRepository;

  /**
   * Repository for Office entities.
   */
  private final OfficeRepository officeRepository;

  /**
   * Repository for Salary entities.
   */
  private final SalaryRepository salaryRepository;

  /**
   * Repository for Position entities.
   */
  private final PositionRepository positionRepository;

  /**
   * Constructs an {@code EmployeeService} with the required repositories.
   *
   * @param employeeRepositoryParam Repository for employee persistence
   * @param addressRepositoryParam  Repository for address persistence
   * @param officeRepositoryParam   Repository for office persistence
   * @param salaryRepositoryParam   Repository for salary persistence
   * @param positionRepositoryParam Repository for position persistence
   */
  public EmployeeService(final EmployeeRepository employeeRepositoryParam,
                         final AddressRepository addressRepositoryParam,
                         final OfficeRepository officeRepositoryParam,
                         final SalaryRepository salaryRepositoryParam,
                         final PositionRepository positionRepositoryParam) {
    this.employeeRepository = employeeRepositoryParam;
    this.addressRepository = addressRepositoryParam;
    this.officeRepository = officeRepositoryParam;
    this.salaryRepository = salaryRepositoryParam;
    this.positionRepository = positionRepositoryParam;
  }

  /**
   * Retrieves all employees from the database.
   *
   * @return a list of all {@link Employee} entities
   */
  public List<Employee> findAll() {
    return employeeRepository.findAll();
  }

  /**
   * Retrieves an employee by its unique identifier.
   *
   * @param id the unique identifier of the employee
   * @return an {@link Optional} containing the employee if found, or empty
   *     otherwise
   */
  public Optional<Employee> findById(final int id) {
    return employeeRepository.findById(id);
  }

  /**
   * Saves a new employee to the database, ensuring all related entities are
   * properly managed.
   *
   * @param employee the employee entity to save
   * @return the persisted {@link Employee} entity
   * @throws RuntimeException if any related entity (office, salary,
   *                          position) is not found
   */
  public Employee save(final Employee employee) {
    Address existingAddress = findOrCreateAddress(employee.getAddress());
    employee.setAddress(existingAddress);

    setManagedRelatedEntities(employee, employee);

    return employeeRepository.save(employee);
  }

  /**
   * Updates an existing employee identified by {@code id} with new data.
   *
   * <p>Updates the employee's personal information and related entities,
   * including address,
   * office, salary, and position.
   * </p>
   *
   * @param id       the ID of the employee to update
   * @param employee the employee data to update
   * @return an {@link Optional} containing the updated employee if found, or
   *     empty otherwise
   */
  public Optional<Employee> update(final int id, final Employee employee) {
    return employeeRepository.findById(id).map(existing -> {
      existing.setFirstName(employee.getFirstName());
      existing.setLastName(employee.getLastName());
      existing.setAccountNumber(employee.getAccountNumber());
      existing.setEmploymentDate(employee.getEmploymentDate());
      existing.setGender(employee.getGender());
      existing.setPersonalIdentificationNumber(
          employee.getPersonalIdentificationNumber());

      updateAddressIfChanged(existing.getAddress(), employee.getAddress(),
          addressRepository);
      setManagedRelatedEntities(existing, employee);

      return employeeRepository.save(existing);
    });
  }

  /**
   * Deletes an employee by its ID.
   *
   * @param id the ID of the employee to delete
   * @return {@code true} if the employee existed and was deleted, {@code
   *     false} otherwise
   */
  public boolean deleteById(final int id) {
    if (employeeRepository.existsById(id)) {
      employeeRepository.deleteById(id);
      return true;
    }
    return false;
  }

  /**
   * Finds an existing address by its fields or creates a new one if it does
   * not exist.
   *
   * @param address the address to find or create
   * @return the existing or newly created {@link Address} entity
   */
  public Address findOrCreateAddress(final Address address) {
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

  /**
   * Sets managed related entities for an existing employee from source
   * employee data.
   *
   * @param target the employee to update
   * @param source the employee containing new related entity IDs
   * @throws RuntimeException if any related entity is not found
   */
  private void setManagedRelatedEntities(final Employee target,
                                         final Employee source) {
    Office managedOffice =
        officeRepository.findById(source.getOffice().getId())
            .orElseThrow(() -> new RuntimeException("Office not found"));
    target.setOffice(managedOffice);

    Salary managedSalary =
        salaryRepository.findById(source.getSalary().getId())
            .orElseThrow(() -> new RuntimeException("Salary not found"));
    target.setSalary(managedSalary);

    Position managedPosition =
        positionRepository.findById(source.getPosition().getId())
            .orElseThrow(() -> new RuntimeException("Position not found"));
    target.setPosition(managedPosition);
  }
}
