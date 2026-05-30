package com.student.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.student.repository.UserRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(UserRepository repository) {

        return username -> repository.findByUsername(username)
                .map(user -> User.withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .authorizeHttpRequests(auth -> auth

                // PUBLIC PAGES
                .requestMatchers(
                		"/",
                        "/home",
                        "/login",
                        "/register",
                        "/css/**",
                        "/js/**",
                        "/images/**"
                ).permitAll()

                // PROTECTED PAGES
                .anyRequest().authenticated()
            )

            .formLogin(login -> login
                .loginPage("/login")
                .loginProcessingUrl("/login")

                // after login go dashboard
                .defaultSuccessUrl("/dashboard", true)

                .failureUrl("/login?error")
                .permitAll()
            )

            .logout(logout -> logout
                // after logout go home page
                .logoutSuccessUrl("/home")
                .permitAll()
            );

        return http.build();
    }
}