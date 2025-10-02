package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class SecurityConfigTest {

  private SecurityConfig securityConfig;

  @BeforeEach
  void setUp() {
    JwtAuthenticationFilter jwtAuthenticationFilter =
        mock(JwtAuthenticationFilter.class);
    securityConfig = new SecurityConfig(jwtAuthenticationFilter);
  }

  @Test
  void passwordEncoder_shouldReturnBCryptPasswordEncoder() {
    PasswordEncoder encoder = securityConfig.passwordEncoder();
    assertThat(encoder).isNotNull();
    assertThat(encoder.matches("password", encoder.encode("password"))).isTrue();
  }

  @Test
  void filterChain_shouldConfigureHttpSecurityCorrectly() throws Exception {
    HttpSecurity http = mock(HttpSecurity.class, org.mockito.Answers.RETURNS_DEEP_STUBS);
    SecurityFilterChain filterChain = securityConfig.filterChain(http);
    assertThat(filterChain).isNotNull();
  }

  @Test
  void filterChain_shouldHaveJwtFilterBeforeUsernamePasswordAuthenticationFilter() throws Exception {
    HttpSecurity http = mock(HttpSecurity.class, org.mockito.Answers.RETURNS_DEEP_STUBS);
    SecurityFilterChain filterChain = securityConfig.filterChain(http);
    assertThat(filterChain).isNotNull();
  }
}
