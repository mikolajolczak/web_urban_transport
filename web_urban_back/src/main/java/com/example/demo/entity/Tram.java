package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

/**
 * Represents a Tram entity in the system.
 *
 * <p>A Tram is a type of Vehicle and contains information about
 * the number of seats, special seats, and wagons.
 * </p>
 */
@Entity
@Table(name = "TRAMS")
public class Tram {

  /**
   * The unique identifier of the tram.
   * This is mapped to the VEHICLE_ID column in the database.
   */
  @Id
  @Column(name = "VEHICLE_ID")
  private int tramId;

  /**
   * The vehicle associated with this tram.
   * Uses a many-to-one relationship with the Vehicle entity
   * and shares the same primary key.
   */
  @ManyToOne
  @MapsId
  @JoinColumn(name = "VEHICLE_ID")
  private Vehicle vehicle;

  /**
   * The total number of standard seats in the tram.
   * Cannot be null.
   */
  @Column(nullable = false)
  private int seatAmount;

  /**
   * The number of special seats (e.g., for disabled passengers) in the tram.
   * Cannot be null.
   */
  @Column(nullable = false)
  private int specialSeatsAmount;

  /**
   * The number of wagons attached to this tram.
   * Cannot be null.
   */
  @Column(nullable = false)
  private int wagonAmount;
}
