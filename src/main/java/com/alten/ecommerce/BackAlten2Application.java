package com.alten.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.alten.ecommerce.repository")
@EntityScan("com.alten.ecommerce.entity")
public class BackAlten2Application {

	public static void main(String[] args) {
		SpringApplication.run(BackAlten2Application.class, args);
	}

}

