package com.github.geng.bread;

import java.util.function.Function;

/**
 * 对象验证，null返回null，可以使用optional类代替
 * @author geng
 */
public class NullOptional {

    /**
     * 封装逻辑，对象为null时返回null
     * @param t 输入判断数据
     * @param mapper jdk8 function
     * @param <T> 输入类型
     * @param <U> 输出类型
     * @return null 或者转换后数据
     */
    public static <T,U> U  validate(T t, Function<? super T, ? extends U> mapper) {
        if (null == t) {
            return null;
        }
        return mapper.apply(t);
    }

}
