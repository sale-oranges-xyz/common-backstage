package com.github.geng.token;

import com.github.geng.exception.NotLoginException;
import com.github.geng.token.info.TokenInfo;

import java.util.Date;

/**
 * <pre>
 *    token 部分抽象接口
 *    具体实现根据使用的token方式处理
 * </pre>
 * @author geng
 */
public interface TokenService {

    /**
     * 从token解析出token数据
     * @param token token
     * @return token数据
     * @throws NotLoginException 用户token 异常信息
     */
    TokenInfo parseToken(String token) throws NotLoginException;

    /**
     * 产生token
     * @param recordId 记录id
     * @param recordName 记录名称
     * @return token
     */
    String generateToken(String recordId, String recordName);

    /**
     * 刷新token
     * @param oldToken 旧token
     * @return 新token
     */
    String refreshToken(String oldToken);

    /**
     * 获取token信息的名称
     * @param token token
     * @return 名称
     * @throws NotLoginException 用户token 异常信息
     */
    String getName(String token) throws NotLoginException;

    /**
     * 获取token 创建时间
     * @param token token
     * @return 创建时间
     * @throws NotLoginException 用户token 异常信息
     */
    Date getCreatedTime(String token) throws NotLoginException;

    /**
     * 获取token 过期时间
     * @param token token
     * @return 过期时间
     * @throws NotLoginException 用户token 异常信息
     */
    Date getExpirationDate(String token) throws NotLoginException;

    /**
     * 判断token是否过期
     * @param token token
     * @return true 过期 | false 没过期
     * @throws NotLoginException 用户token 异常信息
     */
    boolean isTokenExpired(String token) throws NotLoginException;

}
