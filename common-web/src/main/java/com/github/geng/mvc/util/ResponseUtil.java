package com.github.geng.mvc.util;

import com.github.geng.response.ApiResponseData;
import com.github.geng.util.JSONUtil;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * api 响应工具处理类
 * @author geng
 */
public class ResponseUtil {

    /**
     * 创建200返回数据
     * @param data 数据
     * @param <T> 数据类型
     * @return 响应数据
     */
    public static <T> ApiResponseData<T> success(T data) {
        return new ApiResponseData<>(HttpStatus.OK.value(), data);
    }

    /**
     * 创建500返回数据
     * @param message 消息
     * @return 响应数据
     */
    @SuppressWarnings("unchecked")
    public static ApiResponseData error(String message) {
        return new ApiResponseData(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }


    /**
     * 微服务提取响应具体数据
     * @param apiResponseData 微服务请求响应
     * @param <T> 数据类型约束
     * @return 返回具体数据
     */
    public static <T> T extractData(ApiResponseData<T> apiResponseData) {
        if (apiResponseData.getStatus() == HttpStatus.OK.value()) {
            return apiResponseData.getData();
        } else {
            return null;
        }
    }
}
