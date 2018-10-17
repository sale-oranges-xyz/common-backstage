package com.github.geng.token.info;

/**
 * 用户token 接口
 */
public interface Token {

    /**
     * @return token对应的请求头
     */
    String getHeader();

    /**
     * @return token 加密内容
     */
    String getSecret();

    /**
     * token 过期时长
     * @return
     */
    int getExpiration();

    /**
     * @return httpRequest获取的token中额外字符信息
     */
    String getTokenHeader();

    /**
     * @param header 从httpRequest获取的token
     * @return 真正的token
     */
    String getToken(String header);
}
