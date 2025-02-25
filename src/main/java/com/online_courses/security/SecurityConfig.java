package com.online_courses.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    private String EMP = "EMPLOYEE";
    private String MNG = "MANAGER";
    private String ADMIN = "ADMIN";

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(config ->
                config
                        .requestMatchers(HttpMethod.GET, "/api/courses/instructors").hasRole(EMP)
                        .requestMatchers(HttpMethod.GET, "/api/courses/instructors/**").hasRole(EMP)
                        .requestMatchers(HttpMethod.POST, "/api/courses/instructors").hasRole(MNG)
                        .requestMatchers(HttpMethod.PUT, "/api/courses/instructors").hasRole(MNG)
                        .requestMatchers(HttpMethod.DELETE, "/api/courses/instructors/**").hasRole(ADMIN)
                        // instructor-details
                        .requestMatchers(HttpMethod.GET, "/api/courses/instructors/instructor-details/**").hasRole(EMP)
                        .requestMatchers(HttpMethod.POST, "/api/courses/instructors/instructor-details").hasRole(MNG)
                        .requestMatchers(HttpMethod.PUT, "/api/courses/instructors/instructor-details").hasRole(MNG)
                        .requestMatchers(HttpMethod.DELETE, "/api/courses/instructors/instructor-details/**").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/courses/instructors/instructor-details/**").hasRole(MNG)
                        // courses
                        .requestMatchers(HttpMethod.GET, "/api/courses/instructors/courses/**").hasRole(EMP)
                        .requestMatchers(HttpMethod.GET, "/api/courses/**").hasRole(EMP)
                        .requestMatchers(HttpMethod.POST, "/api/courses").hasRole(MNG)
                        .requestMatchers(HttpMethod.PUT, "/api/courses").hasRole(MNG)
                        .requestMatchers(HttpMethod.DELETE, "/api/courses/**").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/courses/**").hasRole(MNG)
                        // reviews
                        .requestMatchers(HttpMethod.POST, "/api/courses/reviews/**").hasRole(MNG)
                        // students
                        .requestMatchers(HttpMethod.GET, "/api/courses/students/**").hasRole(EMP)
                        .requestMatchers(HttpMethod.POST, "/api/courses/students").hasRole(MNG)
                        .requestMatchers(HttpMethod.PUT, "/api/courses/students").hasRole(MNG)
                        .requestMatchers(HttpMethod.DELETE, "/api/courses/students/**").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/courses/students/**").hasRole(ADMIN)
        );

        // use HTTP Basic authentication
        http.httpBasic(Customizer.withDefaults());

        // disable Cross Site Request Forgery (CSRF)
        // in general, not required for stateless REST APIs that use POST, PUT, DELETE and/or PATCH
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
