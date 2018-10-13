package com.github.geng.token.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 产生token 部分信息
 * @author geng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {

    private String id;          // 记录id
    private String name;        // 记录名
    private Date expireTime;    // token 过期时间戳

}
