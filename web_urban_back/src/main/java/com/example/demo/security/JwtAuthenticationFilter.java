package com.example.demo.security;

import com.example.demo.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private static final String JWT_COOKIE_NAME = "jwt-token";

  public JwtAuthenticationFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {

    String jwt = null;

    if (request.getCookies() != null) {
      jwt = Arrays.stream(request.getCookies())
          .filter(cookie -> JWT_COOKIE_NAME.equals(cookie.getName()))
          .map(Cookie::getValue)
          .findFirst()
          .orElse(null);
    }

    if (jwt != null) {
      try {
        String username = jwtService.extractUsername(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
          if (jwtService.isTokenValid(jwt, username)) {
            String role = jwtService.extractRole(jwt);
            SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority("ROLE_" + role);

            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    Collections.singletonList(authority)
                );

            SecurityContextHolder.getContext().setAuthentication(authToken);
          }
        }
      } catch (Exception ignored) {
      }
    }

    filterChain.doFilter(request, response);
  }
}
