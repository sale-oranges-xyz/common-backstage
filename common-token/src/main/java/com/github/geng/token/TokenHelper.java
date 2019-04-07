package com.github.geng.token;

import com.github.geng.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class TokenHelper {

    private List<TokenService> tokenServices;

    public TokenService findMatchService(String type) {
        if (StringUtils.isEmpty(type)) {
            log.debug("系统未开启token处理逻辑");
            return null;
        }

        if (CollectionUtils.isEmpty(tokenServices)) {
            log.debug("系统尚未配置token处理逻辑");
            return null;
        }
        for (TokenService tokenService : tokenServices) {
            if (tokenService.isMatch(type)) {
                return tokenService;
            }
        }

        log.debug("系统未找到匹配的token处理逻辑");
        return null;
    }

    // setters -----------------------------------------------
    @Autowired
    public void setTokenServices(List<TokenService> tokenServices) {
        this.tokenServices = tokenServices;
    }
}
