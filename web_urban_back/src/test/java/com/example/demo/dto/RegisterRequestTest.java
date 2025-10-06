package com.example.demo.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class RegisterRequestTest {

  @Test
  void testUsernameGetterAndSetter() {
    RegisterRequest request = new RegisterRequest();
    String username = "testUser";
    request.setUsername(username);
    assertThat(request.getUsername()).isEqualTo(username);
  }

  @Test
  void testPasswordGetterAndSetter() {
    RegisterRequest request = new RegisterRequest();
    String password = "securePassword";
    request.setPassword(password);
    assertThat(request.getPassword()).isEqualTo(password);
  }

  @Test
  void testRoleGetterAndSetter() {
    RegisterRequest request = new RegisterRequest();
    String role = "ADMIN";
    request.setRole(role);
    assertThat(request.getRole()).isEqualTo(role);
  }

  @Test
  void testEmployeeIdGetterAndSetter() {
    RegisterRequest request = new RegisterRequest();
    Integer employeeId = 123;
    request.setEmployeeId(employeeId);
    assertThat(request.getEmployeeId()).isEqualTo(employeeId);
  }

  @Test
  void testAllPropertiesTogether() {
    RegisterRequest request = new RegisterRequest();
    String username = "user1";
    String password = "pass1";
    String role = "USER";
    Integer employeeId = 456;

    request.setUsername(username);
    request.setPassword(password);
    request.setRole(role);
    request.setEmployeeId(employeeId);

    assertThat(request.getUsername()).isEqualTo(username);
    assertThat(request.getPassword()).isEqualTo(password);
    assertThat(request.getRole()).isEqualTo(role);
    assertThat(request.getEmployeeId()).isEqualTo(employeeId);
  }
}
