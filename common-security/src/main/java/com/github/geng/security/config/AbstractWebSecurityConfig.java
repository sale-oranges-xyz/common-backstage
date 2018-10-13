package com.github.geng.security.config;

import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author geng
 */
public abstract class AbstractWebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 白名单，验证忽略
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**/*.css",
                "/**/*.js",
                "/**/*.png",
                "/**/*.jpg",
                "/**/fonts/**",
                "/images/**",
                "/**/*.ico");
    }

}
