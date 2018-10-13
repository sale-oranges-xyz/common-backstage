package com.github.geng.util;

import java.nio.charset.Charset;
import java.util.Base64;

/**
 * 封装jdk8 的base64编码工具类
 * @author geng
 */
public class Base64Util {
    private Base64Util() {
    }

    /**
     * 编码格式
     */
    private final static Charset charset = Charset.forName("UTF-8");

    /**
     * base64 加密
     * @param str 字符串
     * @return 加密后内容
     */
    public static String encode(String str) {
        if (null == str) {
            return str;
        }
        return Base64.getEncoder().encodeToString(str.getBytes(charset));
    }

    /**
     * base64 解密
     * @param str 字符串
     * @return 解密后内容
     */
    public static String decode(String str) {
        byte[] bytes = Base64.getDecoder().decode(str);
        return new String(bytes, charset);
    }

}
