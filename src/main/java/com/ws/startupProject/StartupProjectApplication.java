package com.ws.startupProject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ws.startupProject.user.User;
import com.ws.startupProject.user.UserRepository;

@SpringBootApplication
public class StartupProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartupProjectApplication.class, args);
    }

    @Bean
    @Profile("developer")
    CommandLineRunner dummyUserAdd(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        return (args) -> {
            for (var i = 1; i <= 25; i++) {
                User user = new User();
                user.setUsername("user" + i);
                user.setEmail("user" + i + "@mail.com");
                user.setPassword(passwordEncoder.encode("P4ssword"));
                user.setActive(i == 2 ? false : true);
                user.setFirstName("first" + i);
                user.setLastName("last" + i);
                if (i == 1) {
                    user.setIsAdministrator(true);
                }
                userRepository.save(user);
            }
        };
    }

}
