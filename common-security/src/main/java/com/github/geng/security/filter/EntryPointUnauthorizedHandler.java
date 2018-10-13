package com.github.geng.security.filter;

import com.github.geng.exception.ErrorMsg;
import com.github.geng.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 定义 401 处理器
 * @author geng
 */
@Component
@Slf4j
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        //返回json形式的错误信息
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");

        log.debug("请求路径:{},token无效", httpServletRequest.getRequestURI());
        httpServletResponse.getWriter().println(JSONUtil.createJson(new ErrorMsg("无效token", HttpStatus.FORBIDDEN.value())));
        httpServletResponse.getWriter().flush();
    }
}
