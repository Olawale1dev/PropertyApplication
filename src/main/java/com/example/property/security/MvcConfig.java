package com.example.property.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigure{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

            registry.addResourceHandler("/uploads/**")
                    .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/");
        }

}
