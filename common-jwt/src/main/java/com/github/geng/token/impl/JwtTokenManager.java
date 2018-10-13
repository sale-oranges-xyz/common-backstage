package com.github.geng.token.impl;

import com.github.geng.exception.NotLoginException;
import com.github.geng.token.TokenService;
import com.github.geng.token.config.UserTokenConfig;
import com.github.geng.token.constant.TokenConstants;
import com.github.geng.token.info.TokenInfo;
import com.github.geng.util.SysStringUtil;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geng
 */
@Slf4j
@Component(value = "JwtTokenService")
public class JwtTokenManager implements TokenService {

    @Autowired
    private UserTokenConfig jwtConfig;

    @Override
    public TokenInfo parseToken(String token) throws NotLoginException {
        final Claims claims = getClaimsFromToken(token);
        Date expiration = claims.getExpiration();
        if (expiration.before(new Date())) {
            throw new NotLoginException("用户token已过期");
        }
        // 获取用户id
        String userId = SysStringUtil.getStringValue(claims.get(TokenConstants.RECORD_ID));
        return new TokenInfo(userId, claims.getSubject(), expiration);
    }

    @Override
    public String getName(String token) throws NotLoginException {
        final Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    @Override
    public Date getCreatedTime(String token) throws NotLoginException {
        final Claims claims = getClaimsFromToken(token);
        return new Date((Long) claims.get(TokenConstants.CLAIM_KEY_CREATED));
    }

    @Override
    public Date getExpirationDate(String token) throws NotLoginException {
        final Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    @Override
    public boolean isTokenExpired(String token) throws NotLoginException {
        final Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }

    @Override
    public String generateToken(String loginName, String userId) throws NotLoginException {
        Map<String, Object> claims = new HashMap<>();
        claims.put(TokenConstants.CLAIM_KEY_USERNAME, loginName);
        claims.put(TokenConstants.CLAIM_KEY_CREATED, new Date());
        claims.put(TokenConstants.RECORD_ID, userId);
        log.debug("根据用户名:{}获取token", loginName);
        return generateToken(claims);
    }

    @Override
    public String refreshToken(String token) {
        final Claims claims = getClaimsFromToken(token);
        claims.put(TokenConstants.CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    //=======================================================
    //private methods
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new NotLoginException("用户token已过期");
        } catch (SignatureException e) {
            throw new NotLoginException("用户token签名错误");
        } catch (IllegalArgumentException e){
            throw new NotLoginException("用户token无效");
        }
        return claims;
    }

    private String generateToken(Map<String, Object> claims) {
        log.debug("开始产生token");
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + jwtConfig.getExpiration() * 60000);
    }

}
