package com.atypon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import java.util.List;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector)
            throws Exception {

        MvcRequestMatcher loginMatcher = new MvcRequestMatcher(introspector, "/login");
        MvcRequestMatcher studentsMatcher = new MvcRequestMatcher(introspector, "/students");
        MvcRequestMatcher coursesMatcher = new MvcRequestMatcher(introspector, "/courses");
        MvcRequestMatcher gradesMatcher = new MvcRequestMatcher(introspector, "/grades");

        System.out.println("Spring Security Initialized...");

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(loginMatcher).permitAll()
                        .requestMatchers(studentsMatcher).hasRole("USER")
                        .requestMatchers(coursesMatcher).hasRole("USER")
                        .requestMatchers(gradesMatcher).hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Custom login page
                        .successHandler(savedRequestAwareAuthenticationSuccessHandler())
                        .defaultSuccessUrl("/students")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler();
    }
    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService, PasswordEncoder encoder) throws Exception {
        return new ProviderManager(List.of(new DaoAuthenticationProvider() {{
            setUserDetailsService(userDetailsService);
            setPasswordEncoder(encoder);
        }}));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}