package com.github.geng.util;

import org.springframework.util.StringUtils;

/**
 * @author geng
 */
public class SysStringUtil extends StringUtils {

    /**
     * 判断字符串数组是否以特定字符开头
     * @param sources 字符串数组
     * @param target 开头字符串
     * @return true 有以target 开头 | false 没有以target开头
     */
    public static boolean isStartWith (String[] sources, String target) {
        if (null == sources || sources.length == 0) {
            return false;
        }
        for (String str : sources) {
            if (str.startsWith(target)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断给定字符串是否以字符串数组的某个元素开头
     * @param target 给定字符串
     * @param sources 字符串数组
     * @return true 有 | false 没有
     */
    public static boolean isStartWith (String target, String[] sources) {
        if (null == sources || sources.length == 0) {
            return false;
        }
        for (String str : sources) {
            if (target.startsWith(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据具体内容分字符串字符串
     * @param sources 分割字符串
     * @param pathSeparate 分割符
     * @return 分割后数组
     */
    public static String[] splitBySpecificSeparate(String sources, String pathSeparate) {
        if (null == sources) {
            return new String[]{};
        }
        if (sources.contains(pathSeparate)) {
            return sources.split(pathSeparate);
        } else {
            return new String[]{ sources };
        }
    }

    /**
     * 获取对象string value
     * @param obj 目标对象
     * @return 空字符串 | 对象toString
     */
    public static String getStringValue(Object obj){
        return obj == null ? "" : obj.toString();
    }

}
