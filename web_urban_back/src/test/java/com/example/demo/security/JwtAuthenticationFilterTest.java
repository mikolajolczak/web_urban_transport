package com.example.demo.security;

import com.example.demo.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JwtAuthenticationFilterTest {

  private JwtService jwtService;
  private JwtAuthenticationFilter filter;
  private HttpServletRequest request;
  private HttpServletResponse response;
  private FilterChain filterChain;

  @BeforeEach
  void setUp() {
    jwtService = mock(JwtService.class);
    filter = new JwtAuthenticationFilter(jwtService);
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    filterChain = mock(FilterChain.class);
    SecurityContextHolder.clearContext();
  }

  @Test
  void doFilterInternal_withValidJwt_setsAuthentication()
      throws ServletException, IOException {
    Cookie jwtCookie = new Cookie("jwt-token", "valid.jwt.token");
    when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});
    when(jwtService.extractUsername("valid.jwt.token")).thenReturn("user");
    when(jwtService.isTokenValid("valid.jwt.token", "user")).thenReturn(true);
    when(jwtService.extractRole("valid.jwt.token")).thenReturn("ADMIN");

    filter.doFilterInternal(request, response, filterChain);

    SecurityContext context = SecurityContextHolder.getContext();
    assertNotNull(context.getAuthentication());
    assertEquals("user", context.getAuthentication().getName());
    assertTrue(context.getAuthentication().getAuthorities()
        .stream()
        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void doFilterInternal_withInvalidJwt_doesNotSetAuthentication()
      throws ServletException, IOException {
    Cookie jwtCookie = new Cookie("jwt-token", "invalid.jwt.token");
    when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});
    when(jwtService.extractUsername("invalid.jwt.token")).thenReturn("user");
    when(jwtService.isTokenValid("invalid.jwt.token", "user")).thenReturn(
        false);

    filter.doFilterInternal(request, response, filterChain);

    SecurityContext context = SecurityContextHolder.getContext();
    assertNull(context.getAuthentication());
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void doFilterInternal_withNoJwtCookie_doesNotSetAuthentication()
      throws ServletException, IOException {
    when(request.getCookies()).thenReturn(
        new Cookie[]{new Cookie("other-cookie", "value")});

    filter.doFilterInternal(request, response, filterChain);

    SecurityContext context = SecurityContextHolder.getContext();
    assertNull(context.getAuthentication());
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void doFilterInternal_withNullCookies_doesNotThrow()
      throws ServletException, IOException {
    when(request.getCookies()).thenReturn(null);

    assertDoesNotThrow(
        () -> filter.doFilterInternal(request, response, filterChain));

    SecurityContext context = SecurityContextHolder.getContext();
    assertNull(context.getAuthentication());
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void doFilterInternal_whenExceptionThrown_doesNotSetAuthentication()
      throws ServletException, IOException {
    Cookie jwtCookie = new Cookie("jwt-token", "broken.jwt.token");
    when(request.getCookies()).thenReturn(new Cookie[]{jwtCookie});
    when(jwtService.extractUsername("broken.jwt.token")).thenThrow(
        new RuntimeException());

    filter.doFilterInternal(request, response, filterChain);

    SecurityContext context = SecurityContextHolder.getContext();
    assertNull(context.getAuthentication());
    verify(filterChain).doFilter(request, response);
  }
}
