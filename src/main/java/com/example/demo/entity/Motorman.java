package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "MOTORMEN")
public class Motorman {
  @Id
  @Column(name = "EMPLOYEE_ID")
  private int motormanId;
  @ManyToOne
  @MapsId
  @JoinColumn(name = "EMPLOYEE_ID")
  private Employee employee;
  @Column(nullable = false)
  private String permitNumber;
  @Column(nullable = false)
  private Date permitExpiryDate;
  @Column(nullable = false)
  private Date medicalCheckExpiryDate;

}
