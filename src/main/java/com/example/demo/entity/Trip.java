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
@Table(name = "TRIPS")
public class Trip {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int tripId;
  @Column(nullable = false)
  private Date startDate;
  @Column(nullable = false)
  private Date endDate;
  @ManyToOne(optional = false)
  @JoinColumn(name = "VEHICLE_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_TRIP_VEHICLE"))
  private Vehicle vehicle;
  @ManyToOne(optional = false)
  @JoinColumn(name = "ROUTE_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_TRIP_ROUTE"))
  private Route route;
}
