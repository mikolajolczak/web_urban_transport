package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Date;

/**
 * Represents an employee in the organization.
 *
 * <p>Contains personal information, employment details, and organizational
 * associations such as office, position, salary, and address.
 * </p>
 */
@Entity
@Table(name = "Employees")
public class Employee {

  /**
   * Unique identifier of the employee.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  /**
   * First name of the employee. Cannot be null.
   */
  @Column(nullable = false)
  private String firstName;

  /**
   * Last name of the employee. Cannot be null.
   */
  @Column(nullable = false)
  private String lastName;

  /**
   * Gender of the employee represented by a single character ('M' or 'F').
   */
  @Column(nullable = false)
  private char gender;

  /**
   * Bank account number of the employee.
   */
  @Column
  private String accountNumber;

  /**
   * Personal identification number (e.g., SSN, PESEL) of the employee.
   */
  @Column
  private String personalIdentificationNumber;

  /**
   * Date when the employee was hired.
   */
  @Column(nullable = false)
  private Date employmentDate;

  /**
   * The office where the employee works.
   *
   * <p>Mapped as a many-to-one relationship to the {@link Office} entity.
   * Cannot be null.
   * </p>
   */
  @ManyToOne
  @JoinColumn(name = "OFFICE_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_EMPLOYEE_OFFICE"))
  private Office office;

  /**
   * The position held by the employee within the organization.
   *
   * <p>Mapped as a many-to-one relationship to the {@link Position} entity.
   * Cannot be null.
   * </p>
   */
  @ManyToOne
  @JoinColumn(name = "POSITION_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_EMPLOYEE_POSITION"))
  private Position position;

  /**
   * The salary associated with the employee.
   *
   * <p>Mapped as a many-to-one relationship to the {@link Salary} entity.
   * Cannot be null.
   * </p>
   */
  @ManyToOne
  @JoinColumn(name = "SALARY_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_EMPLOYEE_SALARY"))
  private Salary salary;

  /**
   * The residential address of the employee.
   *
   * <p>Mapped as a many-to-one relationship to the {@link Address} entity.
   * Cannot be null.
   * </p>
   */
  @ManyToOne
  @JoinColumn(name = "ADDRESS_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_EMPLOYEE_ADDRESS"))
  private Address address;


  /**
   * Returns the unique identifier of the employee.
   *
   * @return employee ID
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the unique identifier of the employee.
   *
   * @param idParam unique employee ID
   */
  public void setId(final int idParam) {
    this.id = idParam;
  }

  /**
   * Returns the first name of the employee.
   *
   * @return first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the first name of the employee.
   *
   * @param firstNameParam employee's first name
   */
  public void setFirstName(final String firstNameParam) {
    this.firstName = firstNameParam;
  }

  /**
   * Returns the last name of the employee.
   *
   * @return last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the last name of the employee.
   *
   * @param lastNameParam employee's last name
   */
  public void setLastName(final String lastNameParam) {
    this.lastName = lastNameParam;
  }

  /**
   * Returns the gender of the employee.
   *
   * @return gender as a character
   */
  public char getGender() {
    return gender;
  }

  /**
   * Sets the gender of the employee.
   *
   * @param genderParam gender character ('M' or 'F')
   */
  public void setGender(final char genderParam) {
    this.gender = genderParam;
  }

  /**
   * Returns the bank account number of the employee.
   *
   * @return account number
   */
  public String getAccountNumber() {
    return accountNumber;
  }

  /**
   * Sets the bank account number of the employee.
   *
   * @param accountNumberParam bank account number
   */
  public void setAccountNumber(final String accountNumberParam) {
    this.accountNumber = accountNumberParam;
  }

  /**
   * Returns the personal identification number of the employee.
   *
   * @return personal ID number
   */
  public String getPersonalIdentificationNumber() {
    return personalIdentificationNumber;
  }

  /**
   * Sets the personal identification number of the employee.
   *
   * @param personalIdentificationNumberParam personal ID number
   */
  public void setPersonalIdentificationNumber(
      final String personalIdentificationNumberParam) {
    this.personalIdentificationNumber = personalIdentificationNumberParam;
  }

  /**
   * Returns the employment date of the employee.
   *
   * @return employment date
   */
  public Date getEmploymentDate() {
    return employmentDate;
  }

  /**
   * Sets the employment date of the employee.
   *
   * @param employmentDateParam date when the employee was hired
   */
  public void setEmploymentDate(final Date employmentDateParam) {
    this.employmentDate = employmentDateParam;
  }

  /**
   * Returns the office associated with the employee.
   *
   * @return {@link Office} entity
   */
  public Office getOffice() {
    return office;
  }

  /**
   * Sets the office for the employee.
   *
   * @param officeParam {@link Office} entity
   */
  public void setOffice(final Office officeParam) {
    this.office = officeParam;
  }

  /**
   * Returns the position of the employee.
   *
   * @return {@link Position} entity
   */
  public Position getPosition() {
    return position;
  }

  /**
   * Sets the position of the employee.
   *
   * @param positionParam {@link Position} entity
   */
  public void setPosition(final Position positionParam) {
    this.position = positionParam;
  }

  /**
   * Returns the salary details of the employee.
   *
   * @return {@link Salary} entity
   */
  public Salary getSalary() {
    return salary;
  }

  /**
   * Sets the salary details for the employee.
   *
   * @param salaryParam {@link Salary} entity
   */
  public void setSalary(final Salary salaryParam) {
    this.salary = salaryParam;
  }

  /**
   * Returns the address of the employee.
   *
   * @return {@link Address} entity
   */
  public Address getAddress() {
    return address;
  }

  /**
   * Sets the address for the employee.
   *
   * @param addressParam {@link Address} entity
   */
  public void setAddress(final Address addressParam) {
    this.address = addressParam;
  }
}
