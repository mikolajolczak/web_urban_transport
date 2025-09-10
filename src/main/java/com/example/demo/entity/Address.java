package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "ADDRESSES", uniqueConstraints = {@UniqueConstraint(
    columnNames = {"STREET", "CITY", "BUILDING_NUMBER", "APARTMENT_NUMBER",
        "POSTAL_CODE"})})
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int addressId;
  @Column(nullable = false)
  private String street;
  @Column(nullable = false)
  private String city;
  @Column(nullable = false)
  private String buildingNumber;
  @Column
  private String apartmentNumber;
  @Column(nullable = false)
  private String postalCode;

  public int getAddressId() {
    return addressId;
  }

  public void setAddressId(int addressIdParam) {
    addressId = addressIdParam;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String streetParam) {
    street = streetParam;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String cityParam) {
    city = cityParam;
  }

  public String getBuildingNumber() {
    return buildingNumber;
  }

  public void setBuildingNumber(String buildingNumberParam) {
    buildingNumber = buildingNumberParam;
  }

  public String getApartmentNumber() {
    return apartmentNumber;
  }

  public void setApartmentNumber(String apartmentNumberParam) {
    apartmentNumber = apartmentNumberParam;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCodeParam) {
    postalCode = postalCodeParam;
  }
}
