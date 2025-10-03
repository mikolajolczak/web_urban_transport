package com.example.demo.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures Cross-Origin Resource Sharing (CORS) mappings for the application.
 *
 * <p>This configuration allows requests from specific origins, enables
 * credentials,
 * and supports common HTTP methods. It is applied globally to all endpoints.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
  /**
   * Customize the CORS mappings for the application.
   *
   * <p>By default, this method allows requests from:
   * <ul>
   *   <li>http://localhost:4200</li>
   *   <li>http://localhost:8080</li>
   * </ul>
   * and permits HTTP methods GET, POST, PUT, DELETE, OPTIONS with any headers.
   *
   * <p>Subclasses may override this method to add or modify CORS mappings, but
   * should call {@code super.addCorsMappings(registry)} to preserve existing
   * mappings.
   *
   * @param registry the {@link CorsRegistry} to configure mappings on
   */
  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("http://localhost:4200", "http://localhost:8080")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true);
  }
}
