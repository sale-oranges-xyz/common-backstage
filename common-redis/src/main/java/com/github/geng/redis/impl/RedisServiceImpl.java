package com.github.geng.redis.impl;

import com.github.geng.cache.ICacheService;
import com.github.geng.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * redis 实现
 * @author geng
 */
@Slf4j
@Service
public class RedisServiceImpl implements ICacheService {

    private StringRedisTemplate redisTemplate;

    @Override
    public <T> Optional<T> get(String key, Class<T> clazz) {
        String result = this.get(key);
        return Optional.ofNullable(JSONUtil.readValue(result, clazz));
    }

    @Override
    public String get(String key) {
        return redisTemplate.execute((RedisCallback<String>)(connection) -> {
                            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                try {
                    return serializer.deserialize(connection.get(serializer.serialize(key)));
                } catch (SerializationException e) {
                    log.error("redis数据反序列化失败", e);
                    this.clearCache(key);
                    return null;
                }
        });
    }

    @Override
    public boolean set(String key, String value, Long expire) {
//        return redisTemplate.execute(new RedisCallback<Boolean>() {
//            @Override
//            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
//                connection.set(serializer.serialize(key), serializer.serialize(value));
//                if (null != expire) {
//                    redisTemplate.expire(key, expire, TimeUnit.SECONDS);
//                }
//                return true;
//            }
//        });
        return redisTemplate.execute((RedisCallback<Boolean>)(connection) -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                if (null != expire) {
                    redisTemplate.expire(key, expire, TimeUnit.SECONDS);
                }
                return true;
        });
    }

    @Override
    public boolean set(String key, Object value, Long expire) {
        String strValue = JSONUtil.createJson(value);
        return this.set(key, strValue, expire);
    }

    @Override
    public <T> boolean setList(String key, List<T> list, Long expire) {
        String value = JSONUtil.createJson(list);
        return this.set(key,value, expire);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clz) {
        return JSONUtil.readListValue(this.get(key));
    }

    @Override
    public void clearCache(String key) {
        redisTemplate.delete(key);
    }

    // -------------------------------------------------------------------------------------------
    // setters
    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
