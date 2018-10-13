package com.github.geng.util;

import javafx.util.converter.IntegerStringConverter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 转换工具类
 * @author geng
 */
@Slf4j
public class ConverterUtils {
    private ConverterUtils() {}

    /**
     * string 转int,无法转换时返回null
     * @param value 输入字符串
     * @return 无法转换时返回null
     */
    public static Integer stringToInt(String value) {
        IntegerStringConverter integerStringConverter = new IntegerStringConverter();
        try {
           return integerStringConverter.fromString(value);
        } catch (NumberFormatException e) {
            log.error("数据解析异常",e);
            return null;
        }
    }

    /**
     * string 转int， 无法转换时使用默认值
     * @param value 输入值
     * @param defaultValue 默认值
     * @return 无法转换时返回null
     */
    public static int StringToIntDefault(String value,int defaultValue) {
        IntegerStringConverter integerStringConverter = new IntegerStringConverter();
        try {
            Optional<Integer> valueOptional = Optional.ofNullable(integerStringConverter.fromString(value));
            return valueOptional.orElse(defaultValue);
        } catch (NumberFormatException e) {
            log.error("数据解析异常",e);
            return defaultValue;
        }
    }

}
