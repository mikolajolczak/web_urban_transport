package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a Route entity in the system.
 *
 * <p>Each route has a unique identifier and a route name.
 * </p>
 */
@Entity
@Table(name = "ROUTES")
public class Route {

  /**
   * Unique identifier for the Route.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  /**
   * Name of the Route.
   * Cannot be null.
   */
  @Column(nullable = false)
  private String routeName;

  /**
   * Retrieves the unique identifier of this Route.
   *
   * @return the id of the route
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the unique identifier of this Route.
   *
   * @param idParam the id to set
   */
  public void setId(final int idParam) {
    this.id = idParam;
  }

  /**
   * Retrieves the name of this Route.
   *
   * @return the routeName
   */
  public String getRouteName() {
    return routeName;
  }

  /**
   * Sets the name of this Route.
   *
   * @param routeNameParam the name of the route; must not be null
   */
  public void setRouteName(final String routeNameParam) {
    this.routeName = routeNameParam;
  }
}
