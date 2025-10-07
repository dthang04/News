package com.bctt.news;

import com.bctt.news.models.User;
import com.bctt.news.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class NewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsApplication.class, args);
	}
	@Bean
	CommandLineRunner init(UserRepository userRepository, PasswordEncoder encoder) {
		return args -> {
			if (userRepository.count() == 0) {
				userRepository.save(new User("admin", encoder.encode("123456"), "ADMIN"));
				userRepository.save(new User("editor", encoder.encode("123456"), "EDITOR"));
				System.out.println("✅ Created default users: admin / editor (password: 123456)");
			}
		};
	}

}
