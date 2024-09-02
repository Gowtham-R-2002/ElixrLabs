package org.medx.elixrlabs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ElixrLabsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElixrLabsApplication.class, args);
		System.out.println("App started");
	}

}
