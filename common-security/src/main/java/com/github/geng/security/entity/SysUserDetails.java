package com.github.geng.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 重新spring security 的 UserDetails
 * 不过目前的微服务体系不需要
 * @author geng
 */
@Deprecated
public class SysUserDetails implements UserDetails {

    public SysUserDetails(String id, String password, String username) {
        this.id = id;
        this.password = password;
        this.username = username;
    }

    private String id;
    private String password;
    private String username;

    public String getId() {
        return this.id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
