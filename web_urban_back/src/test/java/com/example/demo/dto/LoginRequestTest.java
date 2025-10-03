package com.example.demo.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginRequestTest {

  @Test
  void shouldSetAndGetUsername() {
    LoginRequest loginRequest = new LoginRequest();
    String username = "testUser";

    loginRequest.setUsername(username);
    String result = loginRequest.getUsername();

    assertThat(result).isEqualTo(username);
  }

  @Test
  void shouldSetAndGetPassword() {
    LoginRequest loginRequest = new LoginRequest();
    String password = "testPass";

    loginRequest.setPassword(password);
    String result = loginRequest.getPassword();

    assertThat(result).isEqualTo(password);
  }

  @Test
  void shouldHandleNullUsername() {
    LoginRequest loginRequest = new LoginRequest();
    assertThrows(IllegalArgumentException.class, () -> loginRequest.setUsername(null));
  }

  @Test
  void shouldHandleNullPassword() {
    LoginRequest loginRequest = new LoginRequest();
    assertThrows(IllegalArgumentException.class, () -> loginRequest.setPassword(null));
  }
}
