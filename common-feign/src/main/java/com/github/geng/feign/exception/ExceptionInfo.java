package com.github.geng.feign.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/** feign调用异常时的异常包装类
 *  参考 https://www.jianshu.com/p/e6ce817a7dd8
 * @author geng
 */
@Data
@NoArgsConstructor
public class ExceptionInfo {

    private Date timestamp;
    private int status;
    private String error;
    private String exception;
    private String message;
    private String path;

}
