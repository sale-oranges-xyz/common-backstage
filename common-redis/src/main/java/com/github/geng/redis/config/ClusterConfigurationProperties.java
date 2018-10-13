package com.github.geng.redis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * redis 集群配置
 * @author geng
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class ClusterConfigurationProperties {

    /*
     * spring.redis.cluster.nodes[0] = 127.0.0.1:7379
     * spring.redis.cluster.nodes[1] = 127.0.0.1:7380
     * ...
     */
    private List<String> nodes;

    // spring.redis.cluster.password=foobar
    private String password;

    //  spring.redis.cluster.max-redirects=3  // 配置里默认是5
    //  spring.redis.cluster.password=foobar

}
