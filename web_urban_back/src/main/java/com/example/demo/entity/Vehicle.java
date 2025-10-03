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
 * Represents a vehicle entity in the system.
 *
 * <p>This class is mapped to the "VEHICLES" table in the database.
 * It contains information such as brand, model, production year,
 * registration number, purchase date, insurance date, and the associated
 * office.
 * </p>
 */
@Entity
@Table(name = "VEHICLES")
public class Vehicle {

  /**
   * The unique identifier of the vehicle.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  /**
   * The brand of the vehicle (e.g., Toyota, BMW).
   */
  @Column(nullable = false)
  private String brand;

  /**
   * The production year of the vehicle.
   */
  @Column(nullable = false)
  private int productionYear;

  /**
   * The model of the vehicle.
   */
  @Column(nullable = false)
  private String model;

  /**
   * The registration number of the vehicle.
   */
  @Column(nullable = false)
  private String registrationNumber;

  /**
   * The date when the vehicle was purchased.
   */
  @Column(nullable = false)
  private Date purchaseDate;

  /**
   * The date until the vehicle's insurance is valid.
   */
  @Column(nullable = false)
  private Date insuranceDate;

  /**
   * The office to which the vehicle belongs.
   */
  @ManyToOne
  @JoinColumn(name = "OFFICE_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_VEHICLE_OFFICE"))
  private Office office;

  /**
   * Returns the unique identifier of the vehicle.
   *
   * @return the vehicle ID
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the unique identifier of the vehicle.
   *
   * @param vehicleIdParam the vehicle ID to set
   */
  public void setId(final int vehicleIdParam) {
    this.id = vehicleIdParam;
  }

  /**
   * Returns the brand of the vehicle.
   *
   * @return the brand
   */
  public String getBrand() {
    return brand;
  }

  /**
   * Sets the brand of the vehicle.
   *
   * @param brandParam the brand to set
   */
  public void setBrand(final String brandParam) {
    this.brand = brandParam;
  }

  /**
   * Returns the production year of the vehicle.
   *
   * @return the production year
   */
  public int getProductionYear() {
    return productionYear;
  }

  /**
   * Sets the production year of the vehicle.
   *
   * @param productionYearParam the production year to set
   */
  public void setProductionYear(final int productionYearParam) {
    this.productionYear = productionYearParam;
  }

  /**
   * Returns the model of the vehicle.
   *
   * @return the model
   */
  public String getModel() {
    return model;
  }

  /**
   * Sets the model of the vehicle.
   *
   * @param modelParam the model to set
   */
  public void setModel(final String modelParam) {
    this.model = modelParam;
  }

  /**
   * Returns the registration number of the vehicle.
   *
   * @return the registration number
   */
  public String getRegistrationNumber() {
    return registrationNumber;
  }

  /**
   * Sets the registration number of the vehicle.
   *
   * @param registrationNumberParam the registration number to set
   */
  public void setRegistrationNumber(final String registrationNumberParam) {
    this.registrationNumber = registrationNumberParam;
  }

  /**
   * Returns the purchase date of the vehicle.
   *
   * @return the purchase date
   */
  public Date getPurchaseDate() {
    return purchaseDate;
  }

  /**
   * Sets the purchase date of the vehicle.
   *
   * @param purchaseDateParam the purchase date to set
   */
  public void setPurchaseDate(final Date purchaseDateParam) {
    this.purchaseDate = purchaseDateParam;
  }

  /**
   * Returns the insurance expiration date of the vehicle.
   *
   * @return the insurance date
   */
  public Date getInsuranceDate() {
    return insuranceDate;
  }

  /**
   * Sets the insurance expiration date of the vehicle.
   *
   * @param insuranceDateParam the insurance date to set
   */
  public void setInsuranceDate(final Date insuranceDateParam) {
    this.insuranceDate = insuranceDateParam;
  }

  /**
   * Returns the office to which the vehicle is assigned.
   *
   * @return the office
   */
  public Office getOffice() {
    return office;
  }

  /**
   * Sets the office to which the vehicle is assigned.
   *
   * @param officeParam the office to set
   */
  public void setOffice(final Office officeParam) {
    this.office = officeParam;
  }
}
