package com.example.demo.dto;

public class RegisterRequest {
  private String username;
  private String password;
  private String role;
  private Integer employeeId;

  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }

  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }

  public String getRole() { return role; }
  public void setRole(String role) { this.role = role; }

  public Integer getEmployeeId() { return employeeId; }
  public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
}