package ro.iteahome.nhs.adminui.config.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.iteahome.nhs.adminui.service.ServiceUtil;

@Configuration
public class ServiceUtilConfig {

    @Bean
    public ServiceUtil serviceUtil() {
        return new ServiceUtil();
    }
}
