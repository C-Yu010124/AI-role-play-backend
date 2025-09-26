package com.niuniu.airoleplaybackend.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 七牛云服务配置
 * <p>
 * 作者：
 */
@Configuration
@ConfigurationProperties(prefix = "qiniu")
@Data
public class QiniuConfiguration {
    private String qiniuApiKey;
    private String qiniuBaseUrl;
    private String deepseekApiKey;
    private String deepseekBaseUrl;
    private String qiniuAsrUrl;
    private String qiniuTtsUrl;
}
