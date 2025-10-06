package com.example.demo.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.junit.jupiter.api.Assertions.*;

class CorsConfigTest {

  private CorsConfig corsConfig;
  private CorsRegistry registry;

  @BeforeEach
  void setUp() {
    corsConfig = new CorsConfig();
    registry = new TestCorsRegistry();
  }

  @Test
  void addCorsMappings_shouldConfigureCorsCorrectly() {
    corsConfig.addCorsMappings(registry);

    TestCorsRegistry.TestMapping mapping = ((TestCorsRegistry) registry).getMapping();

    assertNotNull(mapping);
    assertArrayEquals(new String[]{"http://localhost:4200", "http://localhost:8080"}, mapping.allowedOrigins);
    assertArrayEquals(new String[]{"GET", "POST", "PUT", "DELETE", "OPTIONS"}, mapping.allowedMethods);
    assertArrayEquals(new String[]{"*"}, mapping.allowedHeaders);
    assertTrue(mapping.allowCredentials);
  }

  static class TestCorsRegistry extends CorsRegistry {

    private TestMapping mapping;

    @Override
    @NonNull
    public CorsRegistration addMapping(@NonNull String pathPattern) {
      mapping = new TestMapping();
      mapping.pathPattern = pathPattern;
      return new CorsRegistration(pathPattern) {
        @Override
        @NonNull
        public CorsRegistration allowedOrigins(@NonNull String... origins) {
          mapping.allowedOrigins = origins;
          return this;
        }

        @Override
        @NonNull
        public CorsRegistration allowedMethods(@NonNull String... methods) {
          mapping.allowedMethods = methods;
          return this;
        }

        @Override
        @NonNull
        public CorsRegistration allowedHeaders(@NonNull String... headers) {
          mapping.allowedHeaders = headers;
          return this;
        }

        @Override
        @NonNull
        public CorsRegistration allowCredentials(boolean allow) {
          mapping.allowCredentials = allow;
          return this;
        }
      };
    }

    public TestMapping getMapping() {
      return mapping;
    }

    static class TestMapping {
      String pathPattern;
      String[] allowedOrigins;
      String[] allowedMethods;
      String[] allowedHeaders;
      boolean allowCredentials;
    }
  }
}
