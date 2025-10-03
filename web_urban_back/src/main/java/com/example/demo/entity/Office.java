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

/**
 * Represents an office entity with a name and an associated address.
 *
 * <p>This class is mapped to the "OFFICES" table in the database.
 * </p>
 */
@Entity
@Table(name = "OFFICES")
public class Office {

  /**
   * The unique identifier of the office.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  /**
   * The name of the office.
   */
  @Column(nullable = false)
  private String officeName;

  /**
   * The address associated with the office.
   */
  @ManyToOne
  @JoinColumn(
      name = "ADDRESS_ID",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_OFFICE_ADDRESS")
  )
  private Address address;

  /**
   * Returns the unique identifier of the office.
   *
   * @return the office ID
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the unique identifier of the office.
   *
   * @param officeIdParam the office ID to set
   */
  public void setId(final int officeIdParam) {
    id = officeIdParam;
  }

  /**
   * Returns the name of the office.
   *
   * @return the office name
   */
  public String getOfficeName() {
    return officeName;
  }

  /**
   * Sets the name of the office.
   *
   * @param officeNameParam the office name to set
   */
  public void setOfficeName(final String officeNameParam) {
    officeName = officeNameParam;
  }

  /**
   * Returns the address associated with the office.
   *
   * @return the address of the office
   */
  public Address getAddress() {
    return address;
  }

  /**
   * Sets the address associated with the office.
   *
   * @param addressParam the address to associate with the office
   */
  public void setAddress(final Address addressParam) {
    address = addressParam;
  }
}
