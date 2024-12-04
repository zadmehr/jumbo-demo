package com.jumbo.store.geo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableMethodSecurity // فعال‌سازی کنترل سطح متدها
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // غیرفعال کردن CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/v1/*",
                    "/swagger-ui.html",      // مسیر Swagger UI
                    "/v1/api-docs/**",       // مسیر مستندات API
                    "/swagger-ui/**" 
                    ).permitAll() // مسیر عمومی
                .anyRequest().authenticated() // سایر مسیرها نیاز به احراز هویت دارند
            )
            .httpBasic(Customizer.withDefaults()); // فعال‌سازی Basic Authentication
        return http.build();
    }
}