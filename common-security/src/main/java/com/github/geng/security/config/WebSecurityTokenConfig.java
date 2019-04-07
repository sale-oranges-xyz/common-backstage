package com.github.geng.security.config;

import com.github.geng.security.filter.AuthenticationTokenFilter;
import com.github.geng.security.filter.EntryPointUnauthorizedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * spring security token 登录配置
 * 参考 https://grokonez.com/spring-framework/spring-security/spring-security-jwt-authentication-restapis-springboot-spring-mvc-spring-security-spring-jpa-mysql
 */
@Configuration
@EnableWebSecurity // 启动security
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnProperty(value = {"security.token"}) // 看情况动态创建bean
public class WebSecurityTokenConfig extends AbstractWebSecurityConfig {

    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 不需要验证地址
                .antMatchers("/swagger-ui.html",
                        "/swagger-resources/configuration/ui",
                        "/swagger-resources",
                        "/swagger-resources/configuration/security",
                        "/v2/api-docs",
                        "/api/v1/admin/login",
                        "/api/v1/admin/logout")
                .permitAll() // 不需要验证路径
                .anyRequest().authenticated()   // 其他地址的访问均需验证权限

                // 配置被拦截时的处理
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(registerEntryPointUnauthorizedHandler())   // 添加 token 无效或者没有携带 token 时的处理
                // .accessDeniedHandler(registerMyAccessDeniedHandler())      //添加无权限时的处理

                // 使用了jwt 不需要session处理
                .and()
                .csrf()
                .disable()                      // 禁用 Spring Security 自带的跨域处理
                .sessionManagement()                        // 定制我们自己的 session 策略
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 调整为让 Spring Security 不创建和使用 session

        // 通过添加过滤器将 token 解析，将用户所有的权限写入本次 Spring Security 的会话
       http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }



    // beans ------------------------------------------------------------------------------------
    // 注册 403 处理器
//    @Bean
//    public MyAccessDeniedHandler registerMyAccessDeniedHandler() {
//        return new MyAccessDeniedHandler();
//    }

    // 注册 401 处理器
    @Bean
    public EntryPointUnauthorizedHandler registerEntryPointUnauthorizedHandler() {
        return new EntryPointUnauthorizedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationTokenFilter authenticationJwtTokenFilter() {
        return new AuthenticationTokenFilter();
    }

    // setters ---------------------------------------------------------------------
    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
