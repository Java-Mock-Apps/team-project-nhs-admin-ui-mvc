package ro.iteahome.nhs.adminui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/home-initial").setViewName("home-initial");
    }

    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolverCreator() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        resolver.setDefaultErrorView("error");
        resolver.setWarnLogCategory("NHS-ADMIN-UI FAIL-SAFE LOGGER"); // TODO: Remove this after completing exception handling. Business exceptions should not be logged.
        return resolver;
    }
}
