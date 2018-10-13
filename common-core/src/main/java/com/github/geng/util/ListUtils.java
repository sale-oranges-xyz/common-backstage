package com.github.geng.util;


import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author geng
 */
public class ListUtils {

    /**
     * 数组集合转list
     * @param array 数据
     * @param <T> 数组类型
     * @return list 数据
     */
    public static <T> List<T> array2List(T[] array) {
        List<T> arrayList = new ArrayList<>();
        if (null == array) {
            return arrayList;
        }
        return Arrays.asList(array);
    }

    /**
     * list 内容检查,null 时返回空ArrayList
     * @param checkList 检查list
     * @param <T> list类型
     * @return checkList |  空ArrayList
     */
    public static <T> List<T> arrayListCheck(List<T> checkList) {
        if (null == checkList) {
            return new ArrayList<>();
        }
        return checkList;
    }
}
