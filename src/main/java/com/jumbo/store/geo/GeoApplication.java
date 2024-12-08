package com.jumbo.store.geo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
// @EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
@OpenAPIDefinition(info = @Info(title = "Geo Store Service API", version = "1.0.0", description = "API for managing store locations and related data"))
public class GeoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeoApplication.class, args);
	}

}
