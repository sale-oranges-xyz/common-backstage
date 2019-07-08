package com.github.geng.response;

import com.github.geng.constant.ResponseConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 封装spring web 的响应 ResponseEntity
 * @author geng
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseData<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    // --------------------------------------------------------------
    private int code;
    private T data;
    // --------------------------------------------------------------
    // constructors
    public ApiResponseData(T data) {
        this.code = ResponseConstants.OK;
        this.data = data;
    }
    // --------------------------------------------------------------
    // methods


}
