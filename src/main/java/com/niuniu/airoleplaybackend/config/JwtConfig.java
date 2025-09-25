package com.niuniu.airoleplaybackend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {
    
    /**
     * JWT密钥
     */
    private String secret;
    
    /**
     * Token有效期（单位：秒）
     */
    private long expiration; // 默认24小时
    
    /**
     * Token前缀
     */
    private String tokenPrefix = "Bearer ";
    
    /**
     * 存放Token的Header Key
     */
    private String header = "Authorization";

}