package com.example.demo.dto;

public class LoginResponse {
  private String token;
  private String role;
  private Integer employeeId;
  private String username;

  public LoginResponse(String token, String role, Integer employeeId, String username) {
    this.token = token;
    this.role = role;
    this.employeeId = employeeId;
    this.username = username;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public Integer getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Integer employeeId) {
    this.employeeId = employeeId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}