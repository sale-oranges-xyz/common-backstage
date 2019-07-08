package com.github.geng.mvc.util;

import com.github.geng.constant.ResponseConstants;
import com.github.geng.response.ApiResponseData;

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
        return new ApiResponseData<>(ResponseConstants.OK, data);
    }

    /**
     * 创建500返回数据
     * @param message 消息
     * @return 响应数据
     */
    @SuppressWarnings("unchecked")
    public static ApiResponseData error(String message) {
        return new ApiResponseData(ResponseConstants.ERROR, message);
    }

    /**
     * 创建返回错误数据，状态码自定义
     * @param message 错误描述信息
     * @param code 状态码
     * @return 响应数据
     */
    @SuppressWarnings("unchecked")
    public static ApiResponseData error(String message, int code) {
        return new ApiResponseData(code, message);
    }
    /**
     * 微服务提取响应具体数据
     * @param apiResponseData 微服务请求响应
     * @param <T> 数据类型约束
     * @return 返回具体数据
     */
    public static <T> T extractData(ApiResponseData<T> apiResponseData) {
        if (apiResponseData.getCode() == ResponseConstants.OK) {
            return apiResponseData.getData();
        } else {
            return null;
        }
    }
}
