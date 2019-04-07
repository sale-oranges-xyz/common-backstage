package com.github.geng.security.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class SysUserDetailsEntity implements SysUserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> grantedAuthorityList;

    // constructors ----------------------------------------------------------
    public SysUserDetailsEntity() {
        this.grantedAuthorityList = new ArrayList<>();
    }

    public SysUserDetailsEntity(String username, String password) {
        this.username = username;
        this.password = password;
        this.grantedAuthorityList = new ArrayList<>();
    }

    public SysUserDetailsEntity(String username, String password, List<GrantedAuthority> grantedAuthorityList) {
        this.username = username;
        this.password = password;
        this.grantedAuthorityList = grantedAuthorityList;
    }
    // -----------------------------------------------------------------------
    @Override
    public boolean isUpdatedPassword(Date tokenExpirationDate) {
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorityList;
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

    // setters ------------------------------------------------------
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
