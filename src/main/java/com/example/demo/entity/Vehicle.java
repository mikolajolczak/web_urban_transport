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
  private int vehicleId;
  @Column(nullable = false)
  private String brand;
  @Column(nullable = false)
  private int productionYear;
  @Column(nullable = false)
  private String model;
  private String registrationNumber;
  @Column(nullable = false)
  private Date purchaseDate;
  private Date insuranceDate;
  @ManyToOne
  @JoinColumn(name = "OFFICE_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_VEHICLE_OFFICE"))
  private Office office;
}
