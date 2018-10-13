package com.github.geng.feign.configuration;

import com.github.geng.constant.ResponseConstants;
import com.github.geng.exception.BizException;
import com.github.geng.exception.ServiceException;
import com.github.geng.feign.exception.ExceptionInfo;
import com.github.geng.util.JSONUtil;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * feign 调用全局异常处理
 * <p>
 *      原理参考 https://www.jianshu.com/p/f240ca7bb7c0
 *      实现参考 https://www.jianshu.com/p/e6ce817a7dd8
 *      异常类型定义参考 https://www.cnblogs.com/yish/p/5850813.html
 *  </p>
 * @author geng
 */
@Slf4j
public class SysErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        // 异常格式
        // {"timestamp":1527398339882,"status":500,"error":"Internal Server Error","exception":"java.lang.StackOverflowError","message":"No message available","path":"/user/validate"}
        try {
            if (null != response.body()) {
                String body = Util.toString(response.body().asReader());
                log.error("feign调用异常,内容:{}", body);
                ExceptionInfo exceptionInfo = JSONUtil.readValue(body, ExceptionInfo.class);

                // 异常完整信息需要返回客户端，客户端需要根据具体异常信息做处理
                //ErrorMsg errorMsg = new ErrorMsg(exceptionInfo.getError(), exceptionInfo.getStatus());

                if (null != exceptionInfo.getException()) {
                    Class clazz = Class.forName(exceptionInfo.getException());
                    Object obj = clazz.newInstance();

                    if (obj instanceof BizException) { // 系统业务逻辑方面异常
                        // return JSONUtil.readValue(targetMsg, BizException.class);
                        return new BizException(exceptionInfo.getError(), exceptionInfo.getStatus());
                    } else { // 系统其它方面异常
                        return new ServiceException(exceptionInfo.getError(), exceptionInfo.getStatus());
                    }
                } else {
                    // 信息输出前端
                    return new ServiceException(exceptionInfo.getError(), exceptionInfo.getStatus());
                    // return JSONUtil.createJson(errorMsg);
                }
            }
        } catch (Exception e) {
            // 显式完整堆栈信息
            log.error("feign解析异常出错", e);
            return new ServiceException(e.getMessage(), ResponseConstants.REMOTE_EXCEPTION);
        }
        return new ServiceException("今天的风儿有点喧嚣", ResponseConstants.REMOTE_EXCEPTION);
    }
}
