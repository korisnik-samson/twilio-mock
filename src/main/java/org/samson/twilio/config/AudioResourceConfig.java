package org.samson.twilio.config;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AudioResourceConfig implements WebMvcConfigurer {

    @Value("${twilio.audio.path:${user.home}/twilio-audio/}")
    private String audioPath;

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/audio/**")
                .addResourceLocations("file:" + audioPath);
    }
}