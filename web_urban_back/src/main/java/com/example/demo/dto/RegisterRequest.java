package com.example.demo.dto;

/**
 * DTO representing a registration request.
 *
 * <p>Contains the necessary information for registering a user, including
 * username,
 * password, role, and an optional employee ID.
 * </p>
 */
public class RegisterRequest {

  /**
   * Username of the registering user.
   */
  private String username;

  /**
   * Password of the registering user.
   */
  private String password;

  /**
   * Role assigned to the registering user.
   */
  private String role;

  /**
   * Employee ID associated with the registering user, if applicable.
   */
  private Integer employeeId;

  /**
   * Returns the username of the registering user.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username of the registering user.
   *
   * @param usernameParam the username to set
   */
  public void setUsername(final String usernameParam) {
    this.username = usernameParam;
  }

  /**
   * Returns the password of the registering user.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password of the registering user.
   *
   * @param passwordParam the password to set
   */
  public void setPassword(final String passwordParam) {
    this.password = passwordParam;
  }

  /**
   * Returns the role assigned to the registering user.
   *
   * @return the role
   */
  public String getRole() {
    return role;
  }

  /**
   * Sets the role assigned to the registering user.
   *
   * @param roleParam the role to set
   */
  public void setRole(final String roleParam) {
    this.role = roleParam;
  }

  /**
   * Returns the employee ID associated with the registering user.
   *
   * @return the employee ID, or null if not set
   */
  public Integer getEmployeeId() {
    return employeeId;
  }

  /**
   * Sets the employee ID associated with the registering user.
   *
   * @param employeeIdParam the employee ID to set
   */
  public void setEmployeeId(final Integer employeeIdParam) {
    this.employeeId = employeeIdParam;
  }
}
