package com.github.geng.exception;

import com.github.geng.constant.ResponseConstants;

/**
 * 用户登录状态异常时抛出的异常
 * @author geng
 */
public class NotLoginException extends BaseException {

    public NotLoginException (String message) {
        super(message, ResponseConstants.USER_INVALID_TOKEN);
    }

}
