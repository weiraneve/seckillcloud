package com.weiran.common.redis.manager;

import com.weiran.common.redis.config.RedisConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
@RequiredArgsConstructor
public class RedisPoolFactory {

	final RedisConfig redisConfig;
	
	@Bean
	public  JedisPool JedisPoolFactory() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
		poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
		poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
		JedisPool jedisPool = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(),
				redisConfig.getTimeout() * 1000, redisConfig.getPassword(), 0);
		return jedisPool;
	}
	
}
