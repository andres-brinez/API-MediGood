package com.farmacia.mediGood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
