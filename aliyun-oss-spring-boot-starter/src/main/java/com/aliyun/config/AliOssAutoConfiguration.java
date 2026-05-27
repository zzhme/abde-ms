package com.aliyun.config;

import com.aliyun.utils.AliOSSUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云OSS自动装配类
 */
//@AutoConfiguration
@Configuration
@EnableConfigurationProperties(AliyunOSSProperties.class)

public class AliOssAutoConfiguration {
    @Bean
    public AliOSSUtils aliOSSUtils(AliyunOSSProperties aliyunOSSProperties) {
        AliOSSUtils aliOSSUtils = new AliOSSUtils();
        aliOSSUtils.setAliyunOSSProperties(aliyunOSSProperties);
        return aliOSSUtils;
    }
}
