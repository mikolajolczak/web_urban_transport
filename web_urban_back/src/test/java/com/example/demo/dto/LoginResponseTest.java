package com.example.demo.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class LoginResponseTest {

  @Test
  void constructorShouldInitializeFieldsCorrectly() {
    LoginResponse response = new LoginResponse("ADMIN", 101, "john.doe");

    assertThat(response.getRole()).isEqualTo("ADMIN");
    assertThat(response.getEmployeeId()).isEqualTo(101);
    assertThat(response.getUsername()).isEqualTo("john.doe");
  }

  @Test
  void setRoleShouldUpdateRole() {
    LoginResponse response = new LoginResponse("USER", 102, "jane.doe");
    response.setRole("ADMIN");

    assertThat(response.getRole()).isEqualTo("ADMIN");
  }

  @Test
  void setEmployeeIdShouldUpdateEmployeeId() {
    LoginResponse response = new LoginResponse("USER", 102, "jane.doe");
    response.setEmployeeId(103);

    assertThat(response.getEmployeeId()).isEqualTo(103);
  }

  @Test
  void setUsernameShouldUpdateUsername() {
    LoginResponse response = new LoginResponse("USER", 102, "jane.doe");
    response.setUsername("john.smith");

    assertThat(response.getUsername()).isEqualTo("john.smith");
  }

  @Test
  void gettersShouldReturnUpdatedValues() {
    LoginResponse response = new LoginResponse("USER", 200, "alice");
    response.setRole("MANAGER");
    response.setEmployeeId(201);
    response.setUsername("bob");

    assertThat(response.getRole()).isEqualTo("MANAGER");
    assertThat(response.getEmployeeId()).isEqualTo(201);
    assertThat(response.getUsername()).isEqualTo("bob");
  }
}
