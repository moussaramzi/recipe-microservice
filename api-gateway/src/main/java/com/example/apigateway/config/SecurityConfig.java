package com.example.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS
                .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
                .authorizeExchange(exchange -> exchange
                        // Allow GET requests to all endpoints without authentication
                        .pathMatchers(HttpMethod.GET, "/**").permitAll()
                        // Require authentication for POST, PUT, and DELETE requests
                        .pathMatchers(HttpMethod.POST, "/**").authenticated()
                        .pathMatchers(HttpMethod.PUT, "/**").authenticated()
                        .pathMatchers(HttpMethod.DELETE, "/**").authenticated()
                        // All other requests need authentication
                        .anyExchange().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults())); // Enable JWT validation

        return serverHttpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Allow your frontend origin
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*")); // Allow all headers
        config.setAllowCredentials(true); // Allow credentials (cookies, authorization headers, etc.)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply CORS settings to all endpoints
        return source;
    }
}
