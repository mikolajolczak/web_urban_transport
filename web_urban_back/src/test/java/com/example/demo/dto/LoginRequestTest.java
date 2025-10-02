package com.example.demo.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

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
    loginRequest.setUsername(null);
    assertThat(loginRequest.getUsername()).isNull();
  }

  @Test
  void shouldHandleNullPassword() {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setPassword(null);
    assertThat(loginRequest.getPassword()).isNull();
  }
}
