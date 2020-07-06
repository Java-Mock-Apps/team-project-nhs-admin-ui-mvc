package ro.iteahome.nhs.adminui.config.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {

    @Bean
    public Logger logger() {
        return LogManager.getLogger("NHS-ADMIN-UI LOGGER");
    }
}
