package com.hotel.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtUtil jwtUtil, @Lazy CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthFilter jwtAuthFilter = new JwtAuthFilter(jwtUtil, userDetailsService);

        http
            .cors(cors -> cors.configure(http)) // Enable CORS with default settings
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            // Public access
            .requestMatchers("/employees/login").permitAll()
            .requestMatchers(HttpMethod.GET, "/rooms/**").permitAll()
            .requestMatchers("/bookings/availability").permitAll()
            .requestMatchers(HttpMethod.POST, "/bookings").permitAll()
        
            // Employee management (Manager only)
            .requestMatchers(HttpMethod.POST, "/employees").hasAuthority("ROLE_Manager")
            .requestMatchers(HttpMethod.PUT, "/employees/**").hasAuthority("ROLE_Manager")
            .requestMatchers(HttpMethod.DELETE, "/employees/**").hasAuthority("ROLE_Manager")
            .requestMatchers(HttpMethod.GET, "/employees").hasAuthority("ROLE_Manager")
        
            // Room management
            .requestMatchers(HttpMethod.GET, "/rooms/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/rooms/**").permitAll()            
            .requestMatchers(HttpMethod.PUT, "/rooms/{id}").hasAuthority("ROLE_Manager")
            .requestMatchers(HttpMethod.DELETE, "/rooms/{id}").hasAuthority("ROLE_Manager")
            .requestMatchers(HttpMethod.PUT, "/rooms/*/amenities").hasAuthority("ROLE_Manager")

        
            // Booking management
            .requestMatchers(HttpMethod.GET, "/bookings").hasAnyAuthority("ROLE_Manager", "ROLE_Employee")
            .requestMatchers(HttpMethod.GET, "/bookings/customer/**").hasAnyAuthority("ROLE_Manager", "ROLE_Employee")
            .requestMatchers(HttpMethod.PUT, "/bookings/*/status").hasAnyAuthority("ROLE_Manager", "ROLE_Employee")
        
            // Renting management
            .requestMatchers(HttpMethod.POST, "/rentings").hasAnyAuthority("ROLE_Manager", "ROLE_Employee")
            .requestMatchers(HttpMethod.POST, "/rentings/from-booking").hasAnyAuthority("ROLE_Manager", "ROLE_Employee")
            .requestMatchers(HttpMethod.GET, "/rentings").hasAuthority("ROLE_Manager")
            .requestMatchers(HttpMethod.GET, "/rentings/customer/**").hasAnyAuthority("ROLE_Manager", "ROLE_Employee")
            .requestMatchers(HttpMethod.PUT, "/rentings/*/checkout").hasAnyAuthority("ROLE_Manager", "ROLE_Employee")
        
            // Hotel chain management (Manager only)
            .requestMatchers(HttpMethod.GET, "/hotel-chains/**").permitAll() // Optional: restrict if needed
            .requestMatchers(HttpMethod.POST, "/hotel-chains").hasAuthority("ROLE_Manager")
            .requestMatchers(HttpMethod.PUT, "/hotel-chains/**").hasAuthority("ROLE_Manager")
            .requestMatchers(HttpMethod.DELETE, "/hotel-chains/**").hasAuthority("ROLE_Manager")
        
            // Hotel management
            .requestMatchers(HttpMethod.GET, "/hotels/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/hotels").hasAuthority("ROLE_Manager")
            .requestMatchers(HttpMethod.PUT, "/hotels/**").hasAuthority("ROLE_Manager")
            .requestMatchers(HttpMethod.DELETE, "/hotels/**").hasAuthority("ROLE_Manager")

            .requestMatchers(HttpMethod.GET, "/hotels/chain/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/bookings/hotel/**").hasAnyAuthority("ROLE_Manager", "ROLE_Employee")



        




                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(authenticationProvider()));
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
