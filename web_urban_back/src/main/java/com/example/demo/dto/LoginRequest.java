package com.example.demo.dto;

/**
 * Data Transfer Object representing a login request.
 *
 * <p>This class encapsulates the username and password required for user
 * authentication.
 * </p>
 */
public class LoginRequest {

  /**
   * The username of the user attempting to log in.
   */
  private String username;

  /**
   * The password of the user attempting to log in.
   */
  private String password;

  /**
   * Returns the username of this login request.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username for this login request.
   *
   * @param usernameParam the username to set
   * @throws IllegalArgumentException if {@code username} is null or empty
   */
  public void setUsername(final String usernameParam) {
    if (usernameParam == null || usernameParam.isEmpty()) {
      throw new IllegalArgumentException("Username cannot be null or empty");
    }
    this.username = usernameParam;
  }

  /**
   * Returns the password of this login request.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password for this login request.
   *
   * @param passwordParam the password to set
   * @throws IllegalArgumentException if {@code password} is null or empty
   */
  public void setPassword(final String passwordParam) {
    if (passwordParam == null || passwordParam.isEmpty()) {
      throw new IllegalArgumentException("Password cannot be null or empty");
    }
    this.password = passwordParam;
  }
}
