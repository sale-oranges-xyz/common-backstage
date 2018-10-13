package com.github.geng.security.filter;

import com.github.geng.exception.ErrorMsg;
import com.github.geng.security.entity.AuthorizeIgnore;
import com.github.geng.security.extra.AuthenticationSerious;
import com.github.geng.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * token 过滤器
 * @author geng
 */
@Slf4j
public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private AuthorizeIgnore authorizeIgnore;
    @Autowired
    private AuthenticationSerious authenticationSerious;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 白名单过滤
        if (authorizeIgnore.isMatch(httpRequest.getRequestURI())) {
            chain.doFilter(request, response);
        }
    }

    private void sendErrorMsg (ServletResponse response) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        ErrorMsg errorMsg = new ErrorMsg("无效token", HttpStatus.FORBIDDEN.value());
        response.getWriter().print(JSONUtil.createJson(errorMsg));
        response.getWriter().flush();
    }
}

