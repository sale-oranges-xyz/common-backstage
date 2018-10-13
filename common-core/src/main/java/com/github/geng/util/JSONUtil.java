package com.github.geng.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geng.bread.NullOptional;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * com.fasterxml.jackson json处理
 * @author geng
 */
@Slf4j
public final class JSONUtil {
    // thread safe
    private static final ObjectMapper objectMapper = new ObjectMapper(); // create once, reuse

    /**
     * 获取json 数据，无法解析时返回null
     * @param value 对象
     * @return json 数据
     */
    public static /* synchronized */ String createJson(Object value) {
        return NullOptional.validate(value, (t) -> {
            try {
                return objectMapper.writeValueAsString(t);
            } catch (JsonProcessingException e) {
                log.error("json转换异常", e);
                return null;
            }
        });
    }

    /**
     * 解析json
     * @param value String 字符串
     * @param clazz 对应类
     * @param <T> 返回类型
     * @return java 对象
     */
    public static <T> T readValue (String value, Class<T> clazz) {
        return NullOptional.validate(value, (t) -> {
            try {
                return objectMapper.readValue(t, clazz);
            } catch (IOException e) {
                log.error("解析json异常", e);
                return null;
            }
        });
    }

    /**
     * 解析list 集合数据
     * @param value 集合数据字符串
     * @param <T> 解析数据类型
     * @return 数据集合或者空list
     */
    public static <T> List<T> readListValue(String value) {
        return NullOptional.validate(value, (t) -> {
            try {
                return objectMapper.readValue(t, new TypeReference<List<T>>() {});
            } catch (IOException e) {
                log.error("解析json异常", e);
                return new ArrayList<>();
            }
        });
    }

}
