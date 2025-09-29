package com.gk.happytteokordersystem.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("https://happydduck-e565b.web.app",
                "https://happydduck-e565b.firebaseapp.com",
                "http://localhost:5173") // 프론트 주소
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true)
    }
}
