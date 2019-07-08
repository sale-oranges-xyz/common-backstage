package com.github.geng.security.entity;

import com.github.geng.constant.DataConstants;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

public class SysUserDetailsEntity implements SysUserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> grantedAuthorityList;
    private int status;

    // constructors ----------------------------------------------------------
    public SysUserDetailsEntity() {
        this.grantedAuthorityList = new ArrayList<>();
    }

    public SysUserDetailsEntity(String username, String password, int status) {
        this(username, password, Collections.emptyList(), status);
    }

    public SysUserDetailsEntity(String username, String password, List<GrantedAuthority> grantedAuthorityList, int status) {
        this.username = username;
        this.password = password;
        this.grantedAuthorityList = grantedAuthorityList;
        this.status = status;
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

        return Boolean.TRUE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isEnabled() {
        return this.status == DataConstants.ENABLE;
    }

    // setters ------------------------------------------------------
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
