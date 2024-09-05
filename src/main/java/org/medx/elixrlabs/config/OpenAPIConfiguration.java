package org.medx.elixrlabs.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080/api/v1/");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("ElixrLabs");
        myContact.setEmail("https://github.com/Gowtham-R-2002/ElixrLabs/");

        Info information = new Info()
                .title("Elixr Labs API")
                .version("1.0")
                .description("This API exposes endpoints to manage sample collectors, patients, admin and tests.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}