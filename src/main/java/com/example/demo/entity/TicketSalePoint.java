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
@Table(name = "TICKETS_SALE_POINTS")
public class TicketSalePoint {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int ticketSalePointId;
  @ManyToOne
  @JoinColumn(name = "POINT_OF_SALE_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_TICKET_SALE_POINT_POINT_OF_SALE"))
  private PointOfSale pointOfSale;
  @ManyToOne
  @JoinColumn(name = "TICKET_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_TICKET_SALE_POINT_TICKET"))
  private Ticket ticket;
  @Column
  private Date purchaseDate;


}
