package com.campus.resourcebooking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Boot Application entry point for Campus Resource Booking System
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.campus.resourcebooking")
@OpenAPIDefinition(
        info = @Info(
                title = "Campus Resource Booking System API",
                version = "1.0.0",
                description = "REST API for managing campus resource bookings including facilities, equipment, and spaces",
                contact = @Contact(
                        name = "Campus IT Support",
                        email = "support@campus.edu"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        )
)
public class CampusResourceBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusResourceBookingApplication.class, args);
    }
}
