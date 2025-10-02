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

@Entity
@Table(name = "BUSSES")
public class Bus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @ManyToOne
  @MapsId
  @JoinColumn(name = "VEHICLE_ID")
  private Vehicle vehicle;
  @Column(nullable = false)
  private int seatAmount;
  @Column(nullable = false)
  private int specialSeatsAmount;

}
