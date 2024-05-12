package com.ws.startupProject;

import com.ws.startupProject.ourWeb.OurWeb;
import com.ws.startupProject.ourWeb.OurWebRepository;
import com.ws.startupProject.user.User;
import com.ws.startupProject.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableJpaRepositories
@SpringBootApplication
public class StartupProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartupProjectApplication.class, args);
    }

    @Bean
    @Profile("developer")
    CommandLineRunner dummyUserAdd(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        return (args) -> {

            var userInDb = userRepository.findByEmail("user1@mail.com");
            if (userInDb != null) {
                return;
            }

            for (var i = 1; i <= 10; i++) {
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

    @Bean
    @Profile("developer")
    CommandLineRunner dummyAddOurWeb(OurWebRepository OurWebRepository) {

        return (args) -> {
            var name = "";
            var InDb = OurWebRepository.findAll();
            if (InDb.size() != 0) {
                return;
            }
            for (var i = 1; i <= 7; i++) {
                OurWeb ourWeb = new OurWeb();

                switch (i) {
                    case 1:
                        name = "Hakkımızda";
                        break;
                    case 2:
                        name = "Vizyonumuz";
                        break;
                    case 3:
                        name = "Misyonumuz";
                        break;
                    case 4:
                        name = "AnaSayfa";
                        break;
                    case 5:
                        name = "AnaManset";
                        break;
                    case 6:
                        name = "AraManset";
                        break;
                    case 7:
                        name = "SonManset";
                        break;
                }
                ourWeb.setName(name);
                ourWeb.setDetail("Detay alanı");
                ourWeb.setImage("default");
                OurWebRepository.save(ourWeb);
            }
        };
    }
}
