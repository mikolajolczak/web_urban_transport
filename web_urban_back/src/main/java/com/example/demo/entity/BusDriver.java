package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.sql.Date;

/**
 * Represents a bus driver in the system.
 *
 * <p>Each bus driver is associated with an employee record and contains
 * information about the driver's license and medical check validity.
 * </p>
 */
@Entity
@Table(name = "BUS_DRIVERS")
public class BusDriver {

  /**
   * Unique identifier for the bus driver.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int busDriverId;

  /**
   * Employee associated with this bus driver.
   *
   * <p>This is a one-to-one relationship where the bus driver corresponds
   * to a single employee.
   * </p>
   */
  @ManyToOne
  @MapsId
  @JoinColumn(name = "EMPLOYEE_ID")
  private Employee employee;

  /**
   * Driver's license number.
   */
  @Column(nullable = false)
  private String driverLicenseNumber;

  /**
   * Expiry date of the driving license.
   */
  @Column(nullable = false)
  private Date drivingLicenseExpiryDate;

  /**
   * Expiry date of the medical check required for driving.
   */
  @Column(nullable = false)
  private Date medicalCheckExpiryDate;

}
