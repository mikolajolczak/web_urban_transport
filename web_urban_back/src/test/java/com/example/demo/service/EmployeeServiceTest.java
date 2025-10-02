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
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;
  @Mock
  private AddressRepository addressRepository;
  @Mock
  private OfficeRepository officeRepository;
  @Mock
  private SalaryRepository salaryRepository;
  @Mock
  private PositionRepository positionRepository;

  @InjectMocks
  private EmployeeService employeeService;

  private Employee employee;
  private Address address;
  private Office office;
  private Salary salary;
  private Position position;

  private AutoCloseable mocks;

  @BeforeEach
  void setUp() {
    mocks = MockitoAnnotations.openMocks(this);
    address = new Address();
    address.setId(1);
    address.setStreet("Main Street");
    address.setCity("City");
    address.setBuildingNumber("10");
    address.setApartmentNumber("5A");
    address.setPostalCode("12345");

    office = new Office();
    office.setId(1);

    salary = new Salary();
    salary.setId(1);

    position = new Position();
    position.setId(1);

    employee = new Employee();
    employee.setId(1);
    employee.setFirstName("John");
    employee.setLastName("Doe");
    employee.setAccountNumber("123456");
    employee.setEmploymentDate(Date.valueOf(LocalDate.now()));
    employee.setGender('M');
    employee.setPersonalIdentificationNumber("11111111111");
    employee.setAddress(address);
    employee.setOffice(office);
    employee.setSalary(salary);
    employee.setPosition(position);
  }

  @AfterEach
  void tearDown() throws Exception {
    mocks.close();
  }

  @Test
  void findAll_ShouldReturnAllEmployees() {
    when(employeeRepository.findAll()).thenReturn(List.of(employee));
    List<Employee> result = employeeService.findAll();
    assertThat(result).hasSize(1).contains(employee);
    verify(employeeRepository).findAll();
  }

  @Test
  void findById_ShouldReturnEmployeeIfExists() {
    when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
    Optional<Employee> result = employeeService.findById(1);
    assertTrue(result.isPresent());
    assertEquals(employee, result.get());
  }

  @Test
  void save_ShouldCreateEmployeeWithManagedEntities() {
    when(
        addressRepository.findByStreetAndCityAndPostalCodeAndBuildingNumberAndApartmentNumber(
            anyString(), anyString(), anyString(), anyString(), anyString()
        )).thenReturn(Optional.of(address));
    when(officeRepository.findById(1)).thenReturn(Optional.of(office));
    when(salaryRepository.findById(1)).thenReturn(Optional.of(salary));
    when(positionRepository.findById(1)).thenReturn(Optional.of(position));
    when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

    Employee result = employeeService.save(employee);
    assertEquals(employee, result);
    verify(employeeRepository).save(employee);
  }

  @Test
  void save_ShouldCreateNewAddressIfNotExists() {
    when(
        addressRepository.findByStreetAndCityAndPostalCodeAndBuildingNumberAndApartmentNumber(
            anyString(), anyString(), anyString(), anyString(), anyString()
        )).thenReturn(Optional.empty());
    when(addressRepository.save(any(Address.class))).thenReturn(address);
    when(officeRepository.findById(1)).thenReturn(Optional.of(office));
    when(salaryRepository.findById(1)).thenReturn(Optional.of(salary));
    when(positionRepository.findById(1)).thenReturn(Optional.of(position));
    when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

    Employee result = employeeService.save(employee);
    assertEquals(employee, result);
    verify(addressRepository).save(address);
    verify(employeeRepository).save(employee);
  }

  @Test
  void update_ShouldUpdateExistingEmployee() {
    Employee updated = new Employee();
    updated.setFirstName("Jane");
    updated.setLastName("Smith");
    updated.setAccountNumber("654321");
    updated.setEmploymentDate(Date.valueOf(LocalDate.now().minusDays(1)));
    updated.setGender('F');
    updated.setPersonalIdentificationNumber("22222222222");
    updated.setAddress(address);
    updated.setOffice(office);
    updated.setSalary(salary);
    updated.setPosition(position);

    when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
    when(addressRepository.save(any(Address.class))).thenReturn(address);
    when(officeRepository.findById(1)).thenReturn(Optional.of(office));
    when(salaryRepository.findById(1)).thenReturn(Optional.of(salary));
    when(positionRepository.findById(1)).thenReturn(Optional.of(position));
    when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

    Optional<Employee> result = employeeService.update(1, updated);
    assertTrue(result.isPresent());
    assertEquals("Jane", result.get().getFirstName());
    assertEquals("Smith", result.get().getLastName());
  }

  @Test
  void deleteById_ShouldDeleteIfExists() {
    when(employeeRepository.existsById(1)).thenReturn(true);
    boolean result = employeeService.deleteById(1);
    assertTrue(result);
    verify(employeeRepository).deleteById(1);
  }

  @Test
  void deleteById_ShouldReturnFalseIfNotExists() {
    when(employeeRepository.existsById(1)).thenReturn(false);
    boolean result = employeeService.deleteById(1);
    assertFalse(result);
    verify(employeeRepository, never()).deleteById(anyInt());
  }

  @Test
  void areAddressesEqual_ShouldReturnTrueForEqualAddresses() {
    EmployeeService service =
        new EmployeeService(employeeRepository, addressRepository,
            officeRepository, salaryRepository, positionRepository);
    assertTrue(service.areAddressesEqual(address, address));
    Address a2 = new Address();
    a2.setStreet("Main Street");
    a2.setCity("City");
    a2.setBuildingNumber("10");
    a2.setApartmentNumber("5A");
    a2.setPostalCode("12345");
    assertTrue(service.areAddressesEqual(address, a2));
  }

  @Test
  void findOrCreateAddress_ShouldReturnExistingAddressIfPresent() {
    when(
        addressRepository.findByStreetAndCityAndPostalCodeAndBuildingNumberAndApartmentNumber(
            anyString(), anyString(), anyString(), anyString(), anyString()
        )).thenReturn(Optional.of(address));
    EmployeeService service =
        new EmployeeService(employeeRepository, addressRepository,
            officeRepository, salaryRepository, positionRepository);
    Address result = service.findOrCreateAddress(address);
    assertEquals(address, result);
  }
}
