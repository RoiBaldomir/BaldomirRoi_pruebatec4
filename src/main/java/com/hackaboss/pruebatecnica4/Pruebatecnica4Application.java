package com.hackaboss.pruebatecnica4;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Pruebatecnica4Application {

	public static void main(String[] args) {
		SpringApplication.run(Pruebatecnica4Application.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {

		return new OpenAPI().info(new Info()
				.title("API reservas")
				.version("0.0.1")
				.description("Permite la gestión de reservas de hoteles y vuelos."));
	}
}
