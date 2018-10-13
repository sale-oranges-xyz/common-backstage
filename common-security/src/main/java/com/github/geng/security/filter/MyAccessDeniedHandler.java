package com.github.geng.security.filter;

import com.github.geng.exception.ErrorMsg;
import com.github.geng.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注册 403 处理器
 * @author geng
 */
@Component
@Slf4j
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        //返回json形式的错误信息
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");

        ErrorMsg errorMsg = new ErrorMsg("token无效", HttpStatus.FORBIDDEN.value());
        httpServletResponse.getWriter().println(JSONUtil.createJson(errorMsg));
        httpServletResponse.getWriter().flush();
    }
}
