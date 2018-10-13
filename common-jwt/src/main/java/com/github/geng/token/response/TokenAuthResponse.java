package com.github.geng.token.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 封装 token 实体类
 * @author geng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenAuthResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String token;

}
