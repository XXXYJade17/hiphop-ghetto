package com.xxxyjade.hiphopghetto.config;

import com.xxxyjade.hiphopghetto.enums.MusicSortType;
import com.xxxyjade.hiphopghetto.inteceptor.JwtInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
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
                        "/api/songs",
                        "/api/upload"
                )
                .excludePathPatterns("/discover.html")
                .excludePathPatterns("/swagger-ui/index.html")
                .excludePathPatterns("/v3/api-docs/**");
    }

    /**
     * 添加枚举转换器
     * @param registry
     */
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Converter<String, MusicSortType>() {
            @Override
            public MusicSortType convert(String source) {
                try {
                    // 先尝试直接查找
                    return MusicSortType.valueOf(source.toUpperCase());
                } catch (IllegalArgumentException e) {
                    // 如果找不到匹配的枚举值，返回默认值
                    return MusicSortType.DEFAULT;
                }
            }
        });
    }
}
