package com.github.geng.security.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;


public interface SysUserDetails extends UserDetails {

    /**
     * @param tokenExpirationDate token 过期时间
     * @return 如果密码在传入时间之前修改，返回true,否则返回false
     */
    boolean isPasswordUpdate(Date tokenExpirationDate);
}
