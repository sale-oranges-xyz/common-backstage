package com.github.geng.mvc.controller;

import com.github.geng.base.UserInterface;
import com.github.geng.constant.DataConstants;
import com.github.geng.exception.NotLoginException;
import com.github.geng.mvc.util.RequestUtil;
import com.github.geng.mvc.util.ResponseUtil;
import com.github.geng.response.ApiResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author geng
 */
public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 从当前request 请求头中获取用户信息
     * @return 用户信息
     */
    protected UserInterface getUserInfo() {
        // 获取token
        HttpServletRequest request = RequestUtil.getRequest();
        String userId = request.getHeader(DataConstants.USER_ID);
        String userName = request.getHeader(DataConstants.USER_NAME);
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(userName)) {
            logger.debug("无法从请求头获取用户信息");
            throw new NotLoginException("用户未登陆");
        }
        return new UserInterface() {
            @Override
            public String getUserId() {
                return userId;
            }
            @Override
            public String getUserName() {
                return userName;
            }
        };
    }

    /**
     * 创建成功响应对象
     * @param data 传输数据
     * @param <T> 约束类型
     * @return 成功响应对象
     */
    protected <T> ApiResponseData<T> success (T data) {
        return ResponseUtil.success(data);
    }

    /**
     * 创建异常响应对象
     * @param message 传输数据
     * @return 异常响应对象
     */
    protected ApiResponseData error (String message) {
        return ResponseUtil.error(message);
    }
}
