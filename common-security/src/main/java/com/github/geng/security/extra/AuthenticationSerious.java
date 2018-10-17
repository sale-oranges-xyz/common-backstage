package com.github.geng.security.extra;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义验证策略
 * @author geng
 */
public interface AuthenticationSerious {

    /**
     * @param httpRequest http请求对象
     * @throws Exception 验证异常
     */
    void authentication(HttpServletRequest httpRequest) throws Exception;
}
