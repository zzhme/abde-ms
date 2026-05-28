package com.zzh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
/**
 * 全局跨域配置（SpringBoot 3.0.2 适配版）
 */
@Configuration(proxyBeanMethods = false) // 3.x 推荐：关闭代理，提升性能
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // 1. Spring 6.x 推荐用 allowedOriginPatterns 替代 allowedOrigins（解决通配符+凭证冲突）
        config.addAllowedOriginPattern("*");
        // 2. 允许凭证传递（跨域携带Cookie/token必备）
        config.setAllowCredentials(true);
        // 3. 允许所有请求头（包含JWT的Authorization头）
        config.addAllowedHeader(CorsConfiguration.ALL);
        // 4. 允许所有HTTP方法
        config.addAllowedMethod(CorsConfiguration.ALL);//跨域预检: OPTIONS
        // 5. 预检请求有效期（3.x 中单位仍为秒）
        config.setMaxAge(3600L);//1小时
        // 6. 暴露自定义响应头（前端需获取的话）
        config.addExposedHeader("Authorization");

        // 注册CORS配置到所有路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}