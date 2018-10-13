package com.github.geng.cache.memory;

import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 参考 https://github.com/lianggzone/springboot-action
 * 需要根据条件初始化
 * @author geng
 */
@Configuration
@ConditionalOnProperty(value = {"spring.cache.guava.enabled"}) // 看情况动态创建bean
public class GuavaCache {

    @Value("${spring.cache.duration}:10")
    private long duration;
    @Value("${spring.cache.maximumSize}:1000")
    private long maximumSize;

    @Bean
    public CacheManager cacheManager() {
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        cacheManager.setCacheBuilder(
                CacheBuilder.newBuilder().
                        expireAfterWrite(duration, TimeUnit.SECONDS).
                        maximumSize(maximumSize)
        );
        return cacheManager;
    }

}
