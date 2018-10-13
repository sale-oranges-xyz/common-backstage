package com.github.geng.redis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author geng
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class SingleNodeConfigurationProperties {

    private String password;         // 密码
    // 127.0.0.1
    private String hostName = "127.0.0.1";         // 连接名称
    private int port = 6379;             // 端口
    private boolean useSingle = true; // 默认使用单机
}
