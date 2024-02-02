package edu.cbsystematics.com.accountingemployeesproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/departments").setViewName("departments");
        registry.addViewController("/positions").setViewName("positions");
        registry.addViewController("/periods").setViewName("periods");
        registry.addViewController("/employees").setViewName("employees");
        registry.addViewController("/pageable").setViewName("pageable");
    }

}