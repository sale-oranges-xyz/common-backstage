package com.github.geng.util;

import java.util.UUID;

/**
 * 系列号获取工具
 * createe by geng
 */
public class SerialNumberUtils {
    private SerialNumberUtils(){}

    /**
     * 获取 uuid
     * @return uuid
     */
    public static String createUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
