package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Represents a physical address entity stored in the database.
 *
 * <p>It maps to the {@code ADDRESSES} table and enforces
 * uniqueness based on the combination of street, city, building number,
 * apartment number, and postal code.
 * </p>
 */
@Entity
@Table(
    name = "ADDRESSES",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {
                "STREET", "CITY", "BUILDING_NUMBER",
                "APARTMENT_NUMBER", "POSTAL_CODE"
            }
        )
    }
)
public class Address {

  /**
   * The unique identifier of the address.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  /**
   * The street name of the address.
   */
  @Column(nullable = false)
  private String street;

  /**
   * The city in which the address is located.
   */
  @Column(nullable = false)
  private String city;

  /**
   * The building number of the address.
   */
  @Column(nullable = false)
  private String buildingNumber;

  /**
   * The apartment number within the building, if applicable.
   */
  @Column
  private String apartmentNumber;

  /**
   * The postal code of the address.
   */
  @Column(nullable = false)
  private String postalCode;

  /**
   * Returns the unique identifier of this address.
   *
   * @return the address identifier
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the unique identifier of this address.
   *
   * @param idParam the address identifier to set
   */
  public void setId(final int idParam) {
    this.id = idParam;
  }

  /**
   * Returns the street name of this address.
   *
   * @return the street name
   */
  public String getStreet() {
    return street;
  }

  /**
   * Sets the street name of this address.
   *
   * @param streetParam the street name to set
   */
  public void setStreet(final String streetParam) {
    this.street = streetParam;
  }

  /**
   * Returns the city of this address.
   *
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * Sets the city of this address.
   *
   * @param cityParam the city to set
   */
  public void setCity(final String cityParam) {
    this.city = cityParam;
  }

  /**
   * Returns the building number of this address.
   *
   * @return the building number
   */
  public String getBuildingNumber() {
    return buildingNumber;
  }

  /**
   * Sets the building number of this address.
   *
   * @param buildingNumberParam the building number to set
   */
  public void setBuildingNumber(final String buildingNumberParam) {
    this.buildingNumber = buildingNumberParam;
  }

  /**
   * Returns the apartment number of this address.
   *
   * @return the apartment number, or {@code null} if not applicable
   */
  public String getApartmentNumber() {
    return apartmentNumber;
  }

  /**
   * Sets the apartment number of this address.
   *
   * @param apartmentNumberParam the apartment number to set
   */
  public void setApartmentNumber(final String apartmentNumberParam) {
    this.apartmentNumber = apartmentNumberParam;
  }

  /**
   * Returns the postal code of this address.
   *
   * @return the postal code
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * Sets the postal code of this address.
   *
   * @param postalCodeParam the postal code to set
   */
  public void setPostalCode(final String postalCodeParam) {
    this.postalCode = postalCodeParam;
  }
}
