package com.github.geng.mvc.controller;

import com.github.geng.exception.BizException;
import com.github.geng.exception.ServiceException;
import com.github.geng.response.ApiResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 使用切面拦截全局异常
 * @author geng
 */
@ControllerAdvice // 可使用 basePackages 捕获特定包异常
@Slf4j
public class SysExceptionHandler extends BaseController {

    @ResponseBody
    @SuppressWarnings("unchecked")
    @ExceptionHandler(value = Exception.class)
    public ApiResponseData<String> ExceptionHandler(HttpServletRequest req, Exception e){
        String url = req.getRequestURI();

        log.error(String.format("路径 %s 请求异常", url),e);

        // 处理客户端输入数据异常,返回成功信息
        if (e instanceof BizException) {
            return super.success(e.getMessage());
        }
        // 服务端异常
        if (e instanceof ServiceException) {
            ServiceException serviceException = (ServiceException)e;
            // 处理服务内部异常
            return super.error(serviceException.getMessage());
        }
        // 最后，不知名异常
        return super.error(e.getMessage());
    }

}

