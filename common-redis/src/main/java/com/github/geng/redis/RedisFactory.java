package com.github.geng.redis;

import com.github.geng.redis.config.ClusterConfigurationProperties;
import com.github.geng.redis.config.SingleNodeConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * redis配置
 * @author geng
 */
//高版本自定义配置文件处理
// @PropertySource(value = "classpath:redisConfig.yml")
// @ConfigurationProperties(prefix = "redis")
@Configuration
public class RedisFactory {

    private ClusterConfigurationProperties clusterProperties;
    private SingleNodeConfigurationProperties singleProperties;

    // 设置集群方式时使用
    @Bean
    public JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory jedisConnectionFactory;
        if (singleProperties.isUseSingle()) { // 使用单机模式
            jedisConnectionFactory = new JedisConnectionFactory();
            jedisConnectionFactory.setPort(singleProperties.getPort());
            if (StringUtils.hasText(singleProperties.getPassword())) {
                jedisConnectionFactory.setPassword(singleProperties.getPassword());
            }
            jedisConnectionFactory.setHostName(singleProperties.getHostName());
        } else { // 使用集群
            // 检查是否有集群配置
            if (CollectionUtils.isEmpty(clusterProperties.getNodes())) {
                throw new RuntimeException("redis 集群未配置，无法启动");
            }
            jedisConnectionFactory = new JedisConnectionFactory(
                    new RedisClusterConfiguration(clusterProperties.getNodes())
            );
            // 设置集群密码，看了半天，源码里没有单个加点设置密码的处理过程
            jedisConnectionFactory.setPassword(clusterProperties.getPassword());
        }
        // 处理Cannot get Jedis connection; nested exception is java.lang.NullPointerEx
        // jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

    @Bean
    public StringRedisTemplate getRedisTemplate() {
        return new StringRedisTemplate(this.connectionFactory());
    }

    // ------------------------------------------------------------------------------
    // setters
    @Autowired
    public void setClusterProperties(ClusterConfigurationProperties clusterProperties) {
        this.clusterProperties = clusterProperties;
    }
    @Autowired
    public void setSingleProperties(SingleNodeConfigurationProperties singleProperties) {
        this.singleProperties = singleProperties;
    }
}
