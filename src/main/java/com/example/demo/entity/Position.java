package com.example.demo.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "POSITIONS")
public class Position {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(nullable = false)
  private String positionName;

  public int getId() {
    return id;
  }

  public void setId(int idParam) {
    id = idParam;
  }

  public String getPositionName() {
    return positionName;
  }

  public void setPositionName(String positionNameParam) {
    positionName = positionNameParam;
  }
}
