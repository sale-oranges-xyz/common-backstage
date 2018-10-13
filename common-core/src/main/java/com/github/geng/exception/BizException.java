package com.github.geng.exception;

import com.github.geng.constant.ResponseConstants;

/**
 * Service 层抛出的可事务回滚异常，主要原因是客户端输入不正确
 * 这种异常必须返回客户端
 * @author geng
 */
public class BizException extends BaseException{

    public BizException(String message) {
        super(message, ResponseConstants.USER_INPUT_ERROR);
    }

    /**
     * 构造含有具体异常状态码的异常信息
     * @param message 消息
     * @param statusCode 状态码
     */
    public BizException(String message, int statusCode) {
        super(message, statusCode);
    }

    @Override
    public String toString() {
        return String.format("{code: %s, message: %s}", this.getStatus(), this.getMessage());
    }

}
