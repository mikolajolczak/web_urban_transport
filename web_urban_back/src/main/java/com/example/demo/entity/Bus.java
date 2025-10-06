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

/**
 * Represents a bus entity within the transportation system.
 * This class maps to the {@code BUSSES} table in the database
 * and defines the relationship with the associated {@link Vehicle}.
 */
@Entity
@Table(name = "BUSSES")
public class Bus {

  /**
   * Unique identifier for the bus.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  /**
   * The vehicle associated with this bus.
   * This field establishes a many-to-one relationship
   * with the {@link Vehicle} entity.
   */
  @ManyToOne
  @MapsId
  @JoinColumn(name = "VEHICLE_ID")
  private Vehicle vehicle;

  /**
   * Total number of regular seats available in the bus.
   * Cannot be {@code null}.
   */
  @Column(nullable = false)
  private int seatAmount;

  /**
   * Total number of special (e.g., priority or accessible) seats available
   * in the bus.
   * Cannot be {@code null}.
   */
  @Column(nullable = false)
  private int specialSeatsAmount;
}
