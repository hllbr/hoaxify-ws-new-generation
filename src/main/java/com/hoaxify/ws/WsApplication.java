package com.hoaxify.ws;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserRepository;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}

	@Bean
	// @Profile("dev")
	CommandLineRunner userCreator(UserRepository userRepository) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return (args) -> {
			for (int i = 0; i < 25; i++) {
				User user = new User();
				user.setUsername("user" + i);
				user.setPassword(passwordEncoder.encode("P4ss$$^word?!*!hal"));
				user.setEmail("user" + i + "@test.com");
				user.setActive(true);
				userRepository.save(user);
			}
		};
	}
}
