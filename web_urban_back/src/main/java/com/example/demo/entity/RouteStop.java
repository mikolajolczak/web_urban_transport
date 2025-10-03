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

/**
 * Represents the association between a {@link Route} and a {@link Stop},
 * including the scheduled arrival time at the stop.
 */
@Entity
public class RouteStop {

  /**
   * Unique identifier of the route stop.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  /**
   * The route to which this stop belongs.
   */
  @ManyToOne
  @JoinColumn(
      name = "ROUTE_ID",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_ROUTE_STOP_ROUTE"))
  private Route route;

  /**
   * The stop associated with the route.
   */
  @ManyToOne
  @JoinColumn(
      name = "STOP_ID",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_ROUTE_STOP_STOP"))
  private Stop stop;

  /**
   * The scheduled arrival time at the stop.
   */
  @Column(nullable = false)
  private Date arrivalTime;

  /**
   * Returns the unique identifier of this route stop.
   *
   * @return the identifier of the route stop
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the unique identifier for this route stop.
   *
   * @param idParam the identifier to set
   */
  public void setId(final int idParam) {
    this.id = idParam;
  }

  /**
   * Returns the route associated with this route stop.
   *
   * @return the route entity
   */
  public Route getRoute() {
    return route;
  }

  /**
   * Sets the route associated with this route stop.
   *
   * @param routeParam the route to set
   */
  public void setRoute(final Route routeParam) {
    this.route = routeParam;
  }

  /**
   * Returns the stop associated with this route stop.
   *
   * @return the stop entity
   */
  public Stop getStop() {
    return stop;
  }

  /**
   * Sets the stop associated with this route stop.
   *
   * @param stopParam the stop to set
   */
  public void setStop(final Stop stopParam) {
    this.stop = stopParam;
  }

  /**
   * Returns the scheduled arrival time at this stop.
   *
   * @return the arrival time
   */
  public Date getArrivalTime() {
    return arrivalTime;
  }

  /**
   * Sets the scheduled arrival time at this stop.
   *
   * @param arrivalTimeParam the arrival time to set
   */
  public void setArrivalTime(final Date arrivalTimeParam) {
    this.arrivalTime = arrivalTimeParam;
  }
}
