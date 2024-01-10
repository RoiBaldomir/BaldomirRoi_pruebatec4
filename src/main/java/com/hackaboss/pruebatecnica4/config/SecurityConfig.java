package com.hackaboss.pruebatecnica4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf().disable()
                .authorizeHttpRequests(authorize ->
                        authorize
                                .dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                                .requestMatchers("/agency/hotels/new").authenticated()
                                .requestMatchers("/agency/hotels/edit/**").authenticated()
                                .requestMatchers("/agency/hotels/delete/**").authenticated()
                                .requestMatchers("/agency/flights/new").authenticated()
                                .requestMatchers("/agency/flights/edit/**").authenticated()
                                .requestMatchers("/agency/flights/delete/**").authenticated()
                                .requestMatchers("/agency/hotel-booking").authenticated()
                                .requestMatchers("/agency/hotel-booking/edit/**").authenticated()
                                .requestMatchers("/agency/hotel-booking/delete/**").authenticated()
                                .requestMatchers("/agency/flight-booking").authenticated()
                                .requestMatchers("/agency/flight-booking/edit/**").authenticated()
                                .requestMatchers("/agency/flight-booking/delete/**").authenticated()
                                .anyRequest().permitAll()
                )
                .formLogin(login -> login
                        .permitAll()
                )
                .httpBasic().and()
                .build();
    }
}

