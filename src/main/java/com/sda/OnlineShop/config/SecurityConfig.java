package com.sda.OnlineShop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.sda.OnlineShop.entities.UserRole.ROLE_SELLER;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/registration").permitAll();
            auth.requestMatchers("/home","/product/**").hasAnyRole("SELLER","BUYER");
            auth.requestMatchers("/addProduct").hasRole("SELLER");
            auth.requestMatchers("/checkout").hasRole("BUYER");
        }).httpBasic();
        httpSecurity.csrf().disable()
                .authorizeHttpRequests().and()
                .cors().disable().authorizeHttpRequests().and()
                .formLogin().loginPage("/logIn").permitAll().defaultSuccessUrl("/home",true);
        return httpSecurity.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers("/img/**","/css/**","/js/**","/vendors/**","/error");
    }

}
