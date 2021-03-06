package com.github.geng.security.filter;

import com.github.geng.constant.ResponseConstants;
import com.github.geng.exception.ErrorMsg;
import com.github.geng.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@Slf4j
// @ConditionalOnProperty(value = {"tokenWeb.enable"}) // 看情况动态创建bean
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException {
        //返回json形式的错误信息
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");

        log.debug("请求路径:{},token无效", httpServletRequest.getRequestURI());
        ErrorMsg errorMsg = new ErrorMsg("token无效", ResponseConstants.USER_INVALID_TOKEN);
        httpServletResponse.getWriter().println(JSONUtil.createJson(errorMsg));
        httpServletResponse.getWriter().flush();
    }
}
