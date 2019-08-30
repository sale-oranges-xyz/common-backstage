package com.github.geng.security.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class SecurityUtils {
    private SecurityUtils() {}


    /**
     * 获取当前已登陆用户的详细信息
     * @param <T>
     * @return
     */
    public static <T extends UserDetails> T getCurUserDetails() {
        // 从安全上下文中获取
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if(null == securityContext) {
            log.debug("当前属于匿名访问");
            return null;
        }

        Authentication authentication = securityContext.getAuthentication();
        if(null == authentication) {
            log.debug("当前属于匿名访问");
            return null;
        }

        Object principal = authentication.getPrincipal();
        if(null == principal) {
            log.debug("当前属于匿名访问");
            return null;
        }

        if(!(principal instanceof UserDetails)) {
            log.debug("当前安全对象:{} 不是一个合法的UserDetails对象", principal);
            return null;
        }

        UserDetails userDetails = (T) principal;
        log.debug("当前登录用户是:{}", userDetails.getUsername());

        return (T)userDetails;
    }
}
