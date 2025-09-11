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


@Entity
@Table(name = "Employees")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int employeeId;
  @Column(nullable = false)
  private String firstName;
  @Column(nullable = false)
  private String lastName;
  @Column(nullable = false)
  private char gender;
  @Column
  private String accountNumber;
  @Column
  private String personalIdentificationNumber;
  @Column(nullable = false)
  private Date employmentDate;
  @ManyToOne
  @JoinColumn(name = "OFFICE_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_EMPLOYEE_OFFICE"))
  private Office office;
  @ManyToOne
  @JoinColumn(name = "POSITION_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_EMPLOYEE_POSITION"))
  private Position position;
  @ManyToOne
  @JoinColumn(name = "SALARY_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_EMPLOYEE_SALARY"))
  private Salary salary;
  @ManyToOne
  @JoinColumn(name = "ADDRESS_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_EMPLOYEE_ADDRESS"))
  private Address address;

  public int getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(int employeeIdParam) {
    employeeId = employeeIdParam;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstNameParam) {
    firstName = firstNameParam;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastNameParam) {
    lastName = lastNameParam;
  }

  public char getGender() {
    return gender;
  }

  public void setGender(char genderParam) {
    gender = genderParam;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumberParam) {
    accountNumber = accountNumberParam;
  }

  public String getPersonalIdentificationNumber() {
    return personalIdentificationNumber;
  }

  public void setPersonalIdentificationNumber(
      String personalIdentificationNumberParam) {
    personalIdentificationNumber = personalIdentificationNumberParam;
  }

  public Date getEmploymentDate() {
    return employmentDate;
  }

  public void setEmploymentDate(Date employmentDateParam) {
    employmentDate = employmentDateParam;
  }

  public Office getOffice() {
    return office;
  }

  public void setOffice(Office officeParam) {
    office = officeParam;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position positionParam) {
    position = positionParam;
  }

  public Salary getSalary() {
    return salary;
  }

  public void setSalary(Salary salaryParam) {
    salary = salaryParam;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address addressParam) {
    address = addressParam;
  }
}
