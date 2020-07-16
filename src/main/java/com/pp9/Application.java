package com.pp9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.pp9.repositories")
@EntityScan(basePackages = "com.pp9.model")
public class Application {

//    @Bean()
//    public UserService userService(PasswordEncoder passwordEncoder, UsersRepository usersRepository, RoleRepository roleRepository) {
//        return new UserServiceImpl(passwordEncoder, usersRepository, roleRepository);
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(UserService userService) {
//        return new UserDetailsServiceImpl(userService);
//    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

