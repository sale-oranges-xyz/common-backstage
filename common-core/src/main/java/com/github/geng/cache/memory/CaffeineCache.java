package com.github.geng.cache.memory;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Caffeine 缓存处理
 * 需要根据条件初始化
 * @author geng
 */
@Configuration
@ConditionalOnProperty(value = {"spring.cache.caffeine.enabled"}) // 看情况动态创建bean
public class CaffeineCache {

    @Value("${spring.cache.duration}:10")
    private long duration;
    @Value("${spring.cache.maximumSize}:1000")
    private long maximumSize;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();

        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .expireAfterWrite(duration, TimeUnit.MINUTES)
                .maximumSize(maximumSize);

        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

}
