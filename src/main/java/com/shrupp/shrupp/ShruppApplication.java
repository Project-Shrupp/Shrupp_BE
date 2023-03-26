package com.shrupp.shrupp;

import com.shrupp.shrupp.domain.post.domain.Post;
import com.shrupp.shrupp.domain.post.repository.PostRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@SpringBootApplication
public class ShruppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShruppApplication.class, args);
	}

}
