package com.niuniu.airoleplaybackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.health.HealthEndpointAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;

@SpringBootApplication(exclude = {
    SqlInitializationAutoConfiguration.class,
    RedisAutoConfiguration.class,
    RedisReactiveAutoConfiguration.class,
    MongoAutoConfiguration.class,
    MongoDataAutoConfiguration.class,
    HealthEndpointAutoConfiguration.class
})
@MapperScan("com.niuniu.airoleplaybackend.dao.mapper")
public class AiRolePlayBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiRolePlayBackendApplication.class, args);
	}

}