package com.example.demo.dto;

public class LoginRequest {
  private String username;
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String usernameParam) {
    username = usernameParam;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String passwordParam) {
    password = passwordParam;
  }
}
