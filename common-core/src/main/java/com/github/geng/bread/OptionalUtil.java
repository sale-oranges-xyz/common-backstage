package com.github.geng.bread;

import java.util.Optional;
import java.util.function.Function;

/**
 *
 * @author geng
 */
public class OptionalUtil {

    /**
     * 封装Optional ofNullable、Map、OrElse一连串操作
     * @param t 输入对象
     * @param mapper 转换逻辑
     * @param <T> 输入类型
     * @param <U> 返回类型
     * @return null 或者转换后对象
     */
    public static <T,U> U ofNullableMapOrElse(T t, Function<? super T, ? extends U> mapper) {
        return Optional.ofNullable(t).map(mapper).orElse(null);
    }

    /**
     * 封装Optional of、Map、get一连串操作，不允许为空
     * @param t 输入对象
     * @param mapper 转换逻辑
     * @param <T> 输入类型
     * @param <U> 返回类型
     * @return 转换后对象
     */
    public static <T,U> U OfMap(T t, Function<? super T, ? extends U> mapper) {
        return Optional.of(t).map(mapper).get();
    }
}
