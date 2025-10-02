package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.sql.Date;

@Entity
public class RouteStop {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @ManyToOne
  @JoinColumn(name = "ROUTE_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_ROUTE_STOP_ROUTE"))
  private Route route;
  @ManyToOne
  @JoinColumn(name = "STOP_ID", nullable = false,
      foreignKey = @ForeignKey(name = "FK_ROUTE_STOP_STOP"))
  private Stop stop;
  @Column(nullable = false)
  private Date arrivalTime;

  public int getId() {
    return id;
  }

  public void setId(int idParam) {
    id = idParam;
  }

  public Route getRoute() {
    return route;
  }

  public void setRoute(Route routeParam) {
    route = routeParam;
  }

  public Stop getStop() {
    return stop;
  }

  public void setStop(Stop stopParam) {
    stop = stopParam;
  }

  public Date getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(Date arrivalTimeParam) {
    arrivalTime = arrivalTimeParam;
  }
}
