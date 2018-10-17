package com.github.geng.security.config;

import com.github.geng.security.filter.AuthenticationTokenFilter;
import com.github.geng.security.filter.EntryPointUnauthorizedHandler;
import com.github.geng.security.filter.MyAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
// import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 单点登录 spring security 配置
 */
// @Configuration
@EnableWebSecurity // 启动security
@ConditionalOnProperty(value = {"tokenWeb.enable"}) // 看情况动态创建bean
public class TokenWebSecurityConfig extends AbstractWebSecurityConfig {
    /**
     * 注册 401 处理器
     */
    @Autowired
    private EntryPointUnauthorizedHandler unauthorizedHandler;
    /**
     * 注册 403 处理器
     */
    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 不需要验证地址
                .antMatchers("/oauth/**","/login/**", "/logout").permitAll() // 不需要验证路径
                .anyRequest().authenticated()   // 其他地址的访问均需验证权限

                // 配置被拦截时的处理
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(this.unauthorizedHandler)   // 添加 token 无效或者没有携带 token 时的处理
                .accessDeniedHandler(this.accessDeniedHandler)      //添加无权限时的处理

                // 使用了jwt 不需要session处理
                .and()
                .csrf()
                .disable()                      // 禁用 Spring Security 自带的跨域处理
                .sessionManagement()                        // 定制我们自己的 session 策略
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 调整为让 Spring Security 不创建和使用 session

        // 通过添加过滤器将 token 解析，将用户所有的权限写入本次 Spring Security 的会话
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }



    // -------------------------------------------------------------------------------------
    /**
     * 注册 token 转换拦截器为 bean
     * 如果客户端传来了 token ，那么通过拦截器解析 token 赋予用户权限
     * @return AuthenticationTokenFilter
     * @throws Exception 异常
     */
    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(super.authenticationManagerBean());
        return authenticationTokenFilter;
    }

}
