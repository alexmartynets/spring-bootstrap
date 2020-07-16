package com.pp9.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.logging.Logger;

@Configuration
@ComponentScan("com.pp9")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    private static Logger log = Logger.getLogger(SecurityConfig.class.getName());

    @Override
    public void configure(HttpSecurity http) throws Exception {
        log.info("START HttpSecurity http");
        http
                .authorizeRequests()
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .antMatchers("/user/**").hasAuthority("USER")
                    .antMatchers("/login").anonymous()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/");
        http.csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("START AuthenticationManagerBuilder auth");
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}