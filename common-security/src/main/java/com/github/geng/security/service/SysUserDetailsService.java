package com.github.geng.security.service;

/**
 * 系统自定义的
 */
public interface SysUserDetailsService {

    SysUserDetails loadUserByUsername(String userName);
}
