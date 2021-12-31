package com.weiran.common.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {

	private String host;

	private int port;

	private int timeout; // 秒

	private String password;

	private int poolMaxTotal;

	private int poolMaxIdle;

	private int poolMaxWait; // 秒
}
