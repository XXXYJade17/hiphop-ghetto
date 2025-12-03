package com.xxxyjade.hiphopghetto.config;

import com.xxxyjade.hiphopghetto.inteceptor.JwtInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    /**
     * 注册拦截器
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册拦截器...");
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns(
                        "/api/albums/**",
                        "/api/songs/**",
                        "/api/comments/**"
                )
                .excludePathPatterns(
                        "/api/albums",
                        "/api/songs"
                )
                .excludePathPatterns("/discover.html")
                .excludePathPatterns("/swagger-ui/index.html")
                .excludePathPatterns("/v3/api-docs/**");
    }

}
