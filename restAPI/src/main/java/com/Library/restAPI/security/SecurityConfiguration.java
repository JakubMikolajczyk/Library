package com.Library.restAPI.security;

import com.Library.restAPI.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html",
                        "/api/v1/all",
                        "/api/v1/auth",
                        "/api/v1/test/**")
                .permitAll()
                .requestMatchers(HttpMethod.GET,
                        "api/v1/books",
                        "api/v1/books/*",
                        "api/v1/books/*/specimens",
                        "api/v1/authors/**",
                        "api/v1/categories/**",
                        "api/v1/specimens/**")
                .permitAll()
                .requestMatchers("/api/v1/users/me/**").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "api/v1/users/**").hasAnyRole("STAFF", "ADMIN")
                .requestMatchers("api/v1/books/**",
                        "api/v1/authors/**",
                        "api/v1/categories/**",
                        "api/v1/specimens/**",
                        "api/v1/borrows/**",
                        "api/v1/borrowHistories/**").hasAnyRole("STAFF", "ADMIN")
                .requestMatchers("api/v1/users/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
        return http.build();
    }
}
