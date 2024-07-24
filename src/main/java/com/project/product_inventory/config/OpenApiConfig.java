package com.project.product_inventory.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Product-Inventory", version = "1.0.0", description = "Aplicacion para manejo de inventario"))
public class OpenApiConfig {

}
