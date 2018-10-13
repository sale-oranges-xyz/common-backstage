package com.github.geng.feign.configuration;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * feign 各种解析器定义
 * @author geng
 */
@Configuration
@EnableFeignClients(basePackages = {"com.github.geng"}) //指定扫描路径
public class FeignConfiguration {

    /**
     * @return 错误解析器
     */
    @Bean
    public ErrorDecoder errorDecoder(){
        return new SysErrorDecoder();
    }

    /**
     * @return 数据解析器
     */
    @Bean
    public Decoder jsonDecoder() {
        return new JacksonDecoder();
    }

    /**
     * @return 数据处理器
     */
    @Bean
    public Encoder jsonEncoder() {
        return new JacksonEncoder();
    }
}
