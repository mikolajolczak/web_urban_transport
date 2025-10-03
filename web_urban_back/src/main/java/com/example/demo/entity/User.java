package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Represents a system user.
 *
 * <p>Each user has a unique username, a password, a role, an active status,
 * and may be associated with an {@link Employee}.
 * </p>
 */
@Entity
@Table(name = "Users")
public class User {

  /**
   * Unique identifier for the user.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  /**
   * Unique username used for authentication.
   */
  @Column(nullable = false, unique = true)
  private String username;

  /**
   * Password for authentication.
   */
  @Column(nullable = false)
  private String password;

  /**
   * Role assigned to the user (e.g., ADMIN, USER).
   */
  @Column(nullable = false)
  private String role;

  /**
   * Whether the user account is active. Default is true.
   */
  @Column(nullable = false)
  private boolean active = true;

  /**
   * Employee entity associated with the user, if any.
   */
  @OneToOne
  @JoinColumn(
      name = "EMPLOYEE_ID",
      foreignKey = @ForeignKey(name = "FK_USER_EMPLOYEE")
  )
  private Employee employee;

  /**
   * Returns the unique ID of the user.
   *
   * @return the user ID
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the unique ID of the user.
   *
   * @param idParam the user ID to set
   */
  public void setId(final int idParam) {
    this.id = idParam;
  }

  /**
   * Returns the username of the user.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username of the user.
   *
   * @param usernameParam the username to set
   */
  public void setUsername(final String usernameParam) {
    this.username = usernameParam;
  }

  /**
   * Returns the role assigned to the user.
   *
   * @return the role
   */
  public String getRole() {
    return role;
  }

  /**
   * Sets the role for the user.
   *
   * @param roleParam the role to set
   */
  public void setRole(final String roleParam) {
    this.role = roleParam;
  }

  /**
   * Returns whether the user account is active.
   *
   * @return true if the user is active, false otherwise
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Sets whether the user account is active.
   *
   * @param activeParam the active status to set
   */
  public void setActive(final boolean activeParam) {
    this.active = activeParam;
  }

  /**
   * Returns the {@link Employee} associated with this user.
   *
   * @return the associated employee, or null if none
   */
  public Employee getEmployee() {
    return employee;
  }

  /**
   * Sets the {@link Employee} associated with this user.
   *
   * @param employeeParam the employee to associate
   */
  public void setEmployee(final Employee employeeParam) {
    this.employee = employeeParam;
  }

  /**
   * Returns the user's password.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the user's password.
   *
   * @param passwordParam the password to set
   */
  public void setPassword(final String passwordParam) {
    this.password = passwordParam;
  }
}
