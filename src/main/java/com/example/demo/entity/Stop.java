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

@Entity
@Table(name = "STOPS")
public class Stop {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int stopId;
  @Column(nullable = false)
  private String stopName;
  @ManyToOne
  @JoinColumn(name = "ADDRESS_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_STOP_ADDRESS"))
  private Address address;
}
