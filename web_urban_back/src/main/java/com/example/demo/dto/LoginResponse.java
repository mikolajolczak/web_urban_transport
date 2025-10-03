package com.example.demo.dto;

/**
 * Represents the response returned after a successful login.
 *
 * <p>This class contains basic user information such as role, employee ID, and
 * username.
 * </p>
 */
public class LoginResponse {

  /**
   * The role assigned to the user (e.g., ADMIN, USER).
   */
  private String role;

  /**
   * The unique identifier of the employee.
   */
  private Integer employeeId;

  /**
   * The username of the logged-in user.
   */
  private String username;

  /**
   * Constructs a new {@code LoginResponse} with the specified details.
   *
   * @param roleParam       the role of the user
   * @param employeeIdParam the unique identifier of the employee
   * @param usernameParam   the username of the user
   */
  public LoginResponse(final String roleParam, final Integer employeeIdParam,
                       final String usernameParam) {
    this.role = roleParam;
    this.employeeId = employeeIdParam;
    this.username = usernameParam;
  }

  /**
   * Returns the role of the user.
   *
   * @return the user's role
   */
  public String getRole() {
    return role;
  }

  /**
   * Sets the role of the user.
   *
   * @param roleParam the role to set
   */
  public void setRole(final String roleParam) {
    this.role = roleParam;
  }

  /**
   * Returns the employee ID of the user.
   *
   * @return the employee ID
   */
  public Integer getEmployeeId() {
    return employeeId;
  }

  /**
   * Sets the employee ID of the user.
   *
   * @param employeeIdParam the employee ID to set
   */
  public void setEmployeeId(final Integer employeeIdParam) {
    this.employeeId = employeeIdParam;
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
}
