package config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI concertBookingOpenAPI() {

        return new OpenAPI()

                .info(new Info()
                        .title("Concert Ticketing API")
                        .description("REST API for Concert Ticket Reservation")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Rahmat Annizar")
                                .email("rahmatannizar@gmail.com"))
                        .license(new License()
                                .name("MIT License")))

                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Environment")
                ))

                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation")
                        .url("https://github.com/rannizar/backend-engineer-technical-test-edts"));
    }

}
