package com.github.geng.util;


/**
 * 系统相关工具类
 */
public class SysUtil {

    /**
     * 判断是否微信小程序注册
     * @return true | false
     */
    public static boolean isMinWxRegister(Integer type) {
        return null != type && type == 0;
    }

    /**
     * 判断是否微信公众号注册
     * @return true | false
     */
    public static boolean isWxRegister(Integer type) {
        return null != type && type == 1;
    }
}
