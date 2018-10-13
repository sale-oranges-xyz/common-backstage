package com.github.geng.exception;

import com.github.geng.constant.ResponseConstants;

/**
 * @author geng
 */
public class ForbiddenException extends BaseException {

    public ForbiddenException (String message) {
        super(message, ResponseConstants.USER_FORBIDDEN_TOKEN);
    }

}
