package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "OFFICES")
public class Office {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(nullable = false)
  private String officeName;
  @OneToOne
  @JoinColumn(name = "ADDRESS_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_OFFICE_ADDRESS"))
  private Address address;

  public int getId() {
    return id;
  }

  public void setId(int officeIdParam) {
    id = officeIdParam;
  }

  public String getOfficeName() {
    return officeName;
  }

  public void setOfficeName(String officeNameParam) {
    officeName = officeNameParam;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address addressParam) {
    address = addressParam;
  }
}
