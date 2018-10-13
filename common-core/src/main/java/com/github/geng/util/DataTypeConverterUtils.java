package com.github.geng.util;

import java.nio.ByteBuffer;

/**
 * 基本数据类型转换工具类
 * @author geng
 */
public class DataTypeConverterUtils {

    /**
     * long 转 byte[]
     * @param x long类型数据
     * @return byte[]
     */
    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(0, x);
        return buffer.array();
    }

}
