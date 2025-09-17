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
@Table(name = "VEHICLES")
public class Vehicle {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(nullable = false)
  private String brand;
  @Column(nullable = false)
  private int productionYear;
  @Column(nullable = false)
  private String model;
  @Column(nullable = false)
  private String registrationNumber;
  @Column(nullable = false)
  private Date purchaseDate;
  @Column(nullable = false)
  private Date insuranceDate;
  @ManyToOne
  @JoinColumn(name = "OFFICE_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_VEHICLE_OFFICE"))
  private Office office;

  public int getId() {
    return id;
  }

  public void setId(int vehicleIdParam) {
    id = vehicleIdParam;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brandParam) {
    brand = brandParam;
  }

  public int getProductionYear() {
    return productionYear;
  }

  public void setProductionYear(int productionYearParam) {
    productionYear = productionYearParam;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String modelParam) {
    model = modelParam;
  }

  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public void setRegistrationNumber(String registrationNumberParam) {
    registrationNumber = registrationNumberParam;
  }

  public Date getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(Date purchaseDateParam) {
    purchaseDate = purchaseDateParam;
  }

  public Date getInsuranceDate() {
    return insuranceDate;
  }

  public void setInsuranceDate(Date insuranceDateParam) {
    insuranceDate = insuranceDateParam;
  }

  public Office getOffice() {
    return office;
  }

  public void setOffice(Office officeParam) {
    office = officeParam;
  }
}
