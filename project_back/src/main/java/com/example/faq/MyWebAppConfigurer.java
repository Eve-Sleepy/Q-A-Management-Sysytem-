package com.example.faq;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 资源映射路径
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = "file:" + System.getProperty("user.dir").replace("\\", "/") + "/upload/image/";
        registry.addResourceHandler("/upload/image/**").addResourceLocations(location);

        String location2 = "file:" + System.getProperty("user.dir").replace("\\", "/") + "/upload/file/";
        registry.addResourceHandler("/upload/file/**").addResourceLocations(location2);

    }
}

