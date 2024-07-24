package com.project.product_inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ProductInventoryApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ProductInventoryApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder aplication) {
		return aplication.sources(ProductInventoryApplication.class);
	}

}
