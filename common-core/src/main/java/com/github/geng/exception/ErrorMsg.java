package com.github.geng.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 异常信息类
 * @author geng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMsg {

    private String msg;
    private int status;

}
