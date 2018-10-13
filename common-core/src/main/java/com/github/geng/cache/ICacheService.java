package com.github.geng.cache;

import java.util.List;
import java.util.Optional;

/**
 * 封装缓存操作通用接口，使用缓存部分一律使用该接口
 * @author geng
 */
public interface ICacheService {

    /**
     * 获取缓存数据
     * @param key 键值
     * @return String 数据
     */
     String get(String key);

    /**
     * 缓存数据
     * @param key 键值
     * @param value String 数据
     * @param expire 过期时间(可为空)，单位：秒
     * @return true 缓存成功 | false 缓存失败
     */
     boolean set(String key, String value, Long expire);

    /**
     * 获取缓存数据
     * @param key 键值
     * @param clazz class 类型
     * @param <T>  数据类型
     * @return true 缓存成功 | false 缓存失败
     */
     <T> Optional<T> get(String key, Class<T> clazz);

     /**
     * 缓存数据
     * @param key 键值
     * @param value 数据
     * @param expire 过期时间(可为空)，单位：秒
     * @return true 缓存成功 | false 缓存失败
     */
    boolean set(String key, Object value, Long expire);

    /**
     * 缓存集合数据
     * @param key 键值
     * @param list 集合数据
     * @param <T> 集合数据类型
     * @param expire 过期时间(可为空)，单位：秒
     * @return true 缓存成功 | false 缓存失败
     */
     <T> boolean setList(String key, List<T> list, Long expire);

    /**
     * 获取缓存集合数据
     * @param key 键值
     * @param clazz 对应类
     * @param <T> 数据类型
     * @return 集合数据
     */
     <T> List<T> getList(String key, Class<T> clazz);

    /**
     * 清除缓存
     * @param key 键值
     */
    void clearCache(String key);
}
