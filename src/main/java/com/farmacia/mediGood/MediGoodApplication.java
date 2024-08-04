package com.farmacia.mediGood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MediGoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediGoodApplication.class, args);
	}

	// configura CORS (Cross-Origin Resource Sharing)
	@Configuration
	public static class Myconfiguration{
		@Bean
		public WebMvcConfigurer corsConfigurer(){
			return new WebMvcConfigurer() {
				@Override
				public void addCorsMappings(CorsRegistry registry) {
					registry.addMapping("/**") // Permite solicitudes de cualquier origer
							.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"); // se especifica que métodos se pueden usar
				}
			};
		}
	}

}
