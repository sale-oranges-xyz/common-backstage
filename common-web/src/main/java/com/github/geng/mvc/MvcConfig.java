package com.github.geng.mvc;

import com.github.geng.mvc.interceptor.RequestInterceptor;
import com.github.geng.mvc.resolver.PageArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 继承 WebMvcConfigurerAdapter 这种做法在 spring5.0 已经被标记为过时
 * @author geng
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 分页数据获取处理
     * @param argumentResolvers 参数处理器集合
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PageArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    /**
     * 拦截器处理操作
     * @param registry 拦截器集合
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor());
        super.addInterceptors(registry);
    }

}
