package com.weiran.common.redis.manager;

import com.weiran.common.redis.key.KeyPrefix;
import com.weiran.common.utils.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
@RequiredArgsConstructor
public class RedisService {

	final JedisPool jedisPool;
	
	/**
	 * 获取单个对象
	 *
	 * 方法返回类型里有<T> T 与 T 两种情况
	 */
	public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			 // 生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			 String str = jedis.get(realKey);
			 T t =  BeanUtil.stringToBean(str, clazz);
			 return t;
		 } finally {
			  returnToPool(jedis);
		 }
	}

	/**
	 * 设置过期时间
	 */
	public void expire(KeyPrefix prefix, String key, int exTime) {
		Jedis jedis = null;
		try {
			jedis =  jedisPool.getResource();
			jedis.expire(prefix.getPrefix() + key, exTime);
		} finally {
			returnToPool(jedis);
		}
	}
	
	/**
	 * 设置对象
	 */
	public <T> boolean set(KeyPrefix prefix, String key, T value, int exTime) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			 String str = BeanUtil.beanToString(value);
			 if (str == null || str.length() <= 0) {
				 return false;
			 }
			// 生成真正的key
			 String realKey = prefix.getPrefix() + key;
			 if (exTime == 0) {
			 	 // 直接保存
				 jedis.set(realKey, str);
			 } else {
			 	 // 设置过期时间
				 jedis.setex(realKey, exTime, str);
			 }
			 return true;
		 } finally {
		 	returnToPool(jedis);
		 }
	}

	/**
	 * 删除redis中指定的key
	 */
	public void delete(KeyPrefix prefix, String key) {
		Jedis jedis = null;
		try {
			jedis =  jedisPool.getResource();
			jedis.del(prefix.getPrefix()+key);
		} finally {
			returnToPool(jedis);
		}
	}

	/**
	 * 判断key是否存在
	 */
	public <T> boolean exists(KeyPrefix prefix, String key) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			//生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			return  jedis.exists(realKey);
		 }finally {
			  returnToPool(jedis);
		 }
	}
	
	/**
	 * 增加值
	 */
	public <T> Long increase(KeyPrefix prefix, String key) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			// 生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			return jedis.incr(realKey);
		 } finally {
			  returnToPool(jedis);
		 }
	}
	
	/**
	 * 减少值
	 */
	public <T> Long decrease(KeyPrefix prefix, String key) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			//生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			return jedis.decr(realKey);
		 } finally {
			  returnToPool(jedis);
		 }
	}

	/**
	 * 收回jedis的连接
	 */
	private void returnToPool(Jedis jedis) {
		 if (jedis != null) {
			 jedis.close();
		 }
	}
}
