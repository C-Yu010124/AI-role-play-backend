package com.niuniu.airoleplaybackend.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OSS服务配置
 * <p>
 * 作者：
 */
@Configuration
@ConfigurationProperties(prefix = "oss")
@Data
public class OssConfiguration {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String region;
    private Long urlExpiration = 3600L; // 默认URL过期时间为1小时

    @Bean(destroyMethod = "shutdown")
    public OSS ossClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
