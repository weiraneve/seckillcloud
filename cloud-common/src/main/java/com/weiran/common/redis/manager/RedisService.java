package com.weiran.common.redis.manager;

import com.weiran.common.redis.key.KeyPrefix;
import com.weiran.common.utils.BeanUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    final RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取单个对象
     * 方法返回类型里有<T> T 与 T 两种情况
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        try {
            // 生成真正的key
            String realKey = prefix.getPrefix() + key;
            String str = (String) redisTemplate.opsForValue().get(realKey);
            return BeanUtil.stringToBean(str, clazz);
        } catch (Exception e) {
            log.error("获取单个对象失败,key为{}, 异常为{}", key, e);
            return null;
        }
    }

    /**
     * 设置过期时间
     */
    public void expire(KeyPrefix prefix, String key, int exTime) {
        try {
            redisTemplate.expire(prefix.getPrefix() + key, exTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("设置过期时间失败,key为{}, 异常为{}", key, e);
        }
    }

    /**
     * 设置对象
     */
    public <T> boolean set(KeyPrefix prefix, String key, T value, int exTime) {
        try {
            String str = BeanUtil.beanToString(value);
            if (str == null || str.length() == 0) {
                return false;
            }
            // 生成真正的key
            String realKey = prefix.getPrefix() + key;
            if (exTime == 0) {
                // 直接保存，过期时间为无限
                redisTemplate.opsForValue().set(realKey, str);
            } else {
                // 设置过期时间
                redisTemplate.opsForValue().set(realKey, str);
                redisTemplate.expire(prefix.getPrefix() + key, exTime, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("设置对象失败,key为{}, 异常为{}", key, e);
            return false;
        }
    }

    /**
     * 删除redis中指定的key
     */
    public void delete(KeyPrefix prefix, String key) {
        try {
            redisTemplate.delete(prefix.getPrefix() + key);
        } catch (Exception e) {
            log.error("删除redis中指定的key失败,key为{}, 异常为{}", key, e);
        }
    }

    /**
     * 判断key是否存在
     */
    public boolean exists(KeyPrefix prefix, String key) {
        try {
            // 生成真正的key
            String realKey = prefix.getPrefix() + key;
            return Boolean.TRUE.equals(redisTemplate.hasKey(realKey));
        } catch (Exception e) {
            log.error("判断key是否存在失败,key为{}, 异常为{}", key, e);
            return false;
        }
    }

    /**
     * 增加值(加1)
     */
    public void increase(KeyPrefix prefix, String key) {
        try {
            // 生成真正的key
            String realKey = prefix.getPrefix() + key;
            redisTemplate.opsForValue().increment(realKey, 1);
        } catch (Exception e) {
            log.error("增加值(加1)失败,key为{}, 异常为{}", key, e);
        }
    }

    /**
     * 获得某个key的前缀存在的内容列表
     */
    public List<String> scanKeysForPattern(String pattern) {
        List<String> keys = new ArrayList<>();
        redisTemplate.execute((connection) -> {
            ScanOptions options = ScanOptions.scanOptions().match(pattern).count(1000).build();
            Cursor<byte[]> cursor = connection.scan(options);
            cursor.forEachRemaining(item -> {
                String key = new String(item);
                keys.add(key);
            });
            return null;
        }, true);
        return keys;
    }
}
