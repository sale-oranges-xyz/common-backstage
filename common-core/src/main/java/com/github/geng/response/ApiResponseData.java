package com.github.geng.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 封装spring web 的响应 ResponseEntity
 * @author geng
 */
@Setter
@Getter
@AllArgsConstructor
public class ApiResponseData<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    // --------------------------------------------------------------
    private int status;
    private T data;
    // --------------------------------------------------------------
    // constructors
    public ApiResponseData() {
        super();
    }
    // --------------------------------------------------------------
    // methods


}
