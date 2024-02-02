package com.ssafy.bid.domain.user.security;

import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return args -> {
            String userIdToCheck = "test4";
            Optional<User> existingUser = repository.findUserById(userIdToCheck);
            if (!existingUser.isPresent()) {
                String encodedPassword = bCryptPasswordEncoder.encode("test4567");
                repository.save(new User(userIdToCheck, encodedPassword, "Test User1", 123));
            }
        };
    }
}