package com.github.geng.token.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * 用户 token 配置读取
 * <pre>
 *  这里会有一个问题出现，如果某一天配置文件的内容需要修改，
 *  照目前这种每个微服务引用的该模式，需要每个服务器都重启，这肯定是违背了微服务松耦合的原则。
 *  所以，用户token解析部分，应该移动到网关，通过网关解析，然后通过请求头传递解析后的用户信息。
 *  但是重新读取的新配置，肯定是无法解密原先的token，
 *  因此程序会强制用户重新登录，这样一来，势必会造成用户的体验很不友好，同时可能前端项目也可能需要修改。
 *  如何协调这个矛盾，需要有时间再想办法，怎样才能做到微服务平稳升级或者降级。
 * </pre>
 * @author geng
 */
@Data
@Configuration
@PropertySource(value = "classpath:jwtConfig.yml")
@ConfigurationProperties(prefix = "jwt", ignoreUnknownFields =false)
public class UserTokenConfig {

    @Value("${header}")
    private String header;
    @Value("${secret}")
    private String secret;
    @Value("${expiration}")
    private int expiration;
    @Value("${tokenHead}")
    private String tokenHeader;

    // -------------------------------------------------
    // public methods
    /**
     * 解析token
     * @param header 请求头获取的授权信息
     * @return 真正token
     */
    public String getToken(String header) {
        if (header.length() <= tokenHeader.length()) {
            return "";
        } else {
            return header.substring(tokenHeader.length()).trim();
        }
    }

}
