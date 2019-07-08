package com.github.geng.util;

import java.util.Collection;
import java.util.function.Predicate;

public class SysCollectionUtils {

    /**
     * 查找集合中对象
     * @param collection 遍历的集合数据
     * @param predicate 判断条件，自己实现
     * @param <T> 对象类型
     * @return null | 查找的对象
     */
    public static <T> T findItem(Collection<T> collection, Predicate<T> predicate) {
        for (T item: collection) {
            if (predicate.test(item)) {
                return item;
            }
        }
        return null;
    }

}
