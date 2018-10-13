package com.github.geng.bread;

import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 封装list stream 操作
 * @author geng
 */
public class CollectionOptional {

    /**
     *  封装Arraylist steam的map转换流
     * @param collection 输入集合
     * @param mapper map处理逻辑
     * @param <T> 输入集合数据类型
     * @param <U> 返回ArrayList的数据类型
     * @return 转换后的ArrayList
     */
    public static <T,U> List<U> mapToArrayList(Collection<T> collection, Function<? super T, ? extends U> mapper) {
        if (CollectionUtils.isEmpty(collection)) {
            return new ArrayList<>();
        }
        //map是转换流
        return collection.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 封装HashSet stream的map转换流
     * @param collection 输入集合
     * @param mapper map处理逻辑
     * @param <T> 输入集合数据类型
     * @param <U> 输出HashSet数据类型
     * @return 转换后的HashSet
     */
    public static <T,U> Set<U> mapToHashSet(Collection<T> collection, Function<? super T, ? extends U> mapper) {
        if (CollectionUtils.isEmpty(collection)) {
            return new HashSet<>();
        }
        return collection.stream().map(mapper).collect(Collectors.toSet());
    }


//    public static void main(String[] args) {
//        List<Integer> orignalList = Arrays.asList(1);
//        List<String> targetList = CollectionOptional.mapToArrayList(orignalList, String::valueOf);
//        targetList.forEach(System.out::println);
//    }
}
