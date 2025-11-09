package com.example.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(authz -> authz
                        .pathMatchers("/auth/**").permitAll()
                        .pathMatchers("/api/**").permitAll()  // ← Filter'ın işlemesine izin ver
                        .anyExchange().permitAll()
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}