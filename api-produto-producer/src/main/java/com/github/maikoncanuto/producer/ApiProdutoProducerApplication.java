package com.github.maikoncanuto.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiProdutoProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiProdutoProducerApplication.class, args);
	}

}
