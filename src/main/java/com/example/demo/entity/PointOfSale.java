package com.example.demo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "POINTS_OF_SALE")
public class PointOfSale {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int pointOfSaleId;
  @ManyToOne
  @JoinColumn(name = "ADDRESS_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_POINT_OF_SALE_ADDRESS"))
  private Address address;
  @ManyToOne
  @JoinColumn(name = "OFFICE_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_POINT_OF_SALE_OFFICE"))
  private Office office;
}
