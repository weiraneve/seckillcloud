package com.weiran.common.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RedissonClient 配置类
 */
@Configuration
public class RedissonConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.password}")
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        // 开启单机，还有集群配置
        config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setPassword(password);
        RedissonClient redisson = Redisson.create(config);

        return redisson;
    }
}
