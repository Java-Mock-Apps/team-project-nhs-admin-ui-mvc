package ro.iteahome.nhs.adminui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("welcome");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/home-initial").setViewName("home-initial");
    }

    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolverCreator() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();

//        mappings.setProperty("DatabaseException", "databaseError"); // TODO: Figure out how to develop these examples.
//        mappings.setProperty("ValidationException", "validationError");

        resolver.setExceptionMappings(mappings);
        resolver.setDefaultErrorView("error/test-error");
        resolver.setWarnLogCategory("NHS-ADMIN-UI FAIL-SAFE LOGGER"); // TODO: Remove this after completing exception handling. Business exceptions should not be logged.

        return resolver;
    }
}
