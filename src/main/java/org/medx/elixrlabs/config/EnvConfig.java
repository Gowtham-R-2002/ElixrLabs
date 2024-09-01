package org.medx.elixrlabs.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>This class manages the loading of environment variables
 * for the application. It provides access to the configuration
 * details needed during runtime.</p>
 *
 * @author Gowtham R
 */
@Configuration
public class EnvConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure().load();
    }
}
