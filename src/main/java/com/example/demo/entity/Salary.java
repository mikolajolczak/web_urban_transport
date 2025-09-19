package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Salaries")
public class Salary {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(nullable = false)
  private int amount;

  public int getId() {
    return id;
  }

  public void setId(int idParam) {
    id = idParam;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amountParam) {
    amount = amountParam;
  }
}
