package com.example.demo.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ROUTES")
public class Route {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int routeId;
  @Column(nullable = false)
  private String routeName;


}
