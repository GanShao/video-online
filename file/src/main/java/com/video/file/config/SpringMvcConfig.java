package com.video.file.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //全路径地址：http://127.0.0.1:9003/file/f/fileImage/XXXXX-XX.jpg
        //这里的"/f" 就是后面的本地路径
        registry.addResourceHandler("/f/**").addResourceLocations("file:/Users/gs/IdeaProjects/video-player/doc/");
    }
}
