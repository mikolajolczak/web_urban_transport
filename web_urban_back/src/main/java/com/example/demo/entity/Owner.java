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
@Table(name = "OWNERS")
public class Owner {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String surname;
  @Column(nullable = false)
  private String phone;
  @Column(nullable = false)
  private String email;
  @ManyToOne
  @JoinColumn(name = "OFFICE_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_OWNER_OFFICE"))
  private Office office;
}
