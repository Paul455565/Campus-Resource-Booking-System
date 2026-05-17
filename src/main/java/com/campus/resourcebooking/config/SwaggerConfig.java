package com.campus.resourcebooking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI Configuration for Campus Resource Booking System
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Campus Resource Booking System API")
                        .version("1.0.0")
                        .description("Comprehensive REST API for managing campus resource bookings, including facilities, equipment, and spaces. " +
                                "The system provides endpoints for booking management, resource administration, user management, " +
                                "approval workflows, maintenance scheduling, and notification delivery.")
                        .contact(new Contact()
                                .name("Campus IT Support")
                                .email("support@campus.edu")
                                .url("https://campus.edu/support"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .addServersItem(new Server()
                        .url("http://localhost:8080/api")
                        .description("Development Server"))
                .addServersItem(new Server()
                        .url("https://api.campus.edu")
                        .description("Production Server"));
    }
}
