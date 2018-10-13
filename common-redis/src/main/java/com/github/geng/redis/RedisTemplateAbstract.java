package com.github.geng.redis;

import com.github.geng.redis.entity.RedisModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * 模仿StringRedisTemplate
 * 当redisTemplate value存储使用对象时，可继承该类进行redisTemplate创建
 * 同时封装一些通用操作
 * 详情查看 {@link StringRedisTemplate}
 * </pre>
 * @param <T> T 为redisTemplate的value对象且继承了RedisModel
 * @author geng
 */
public abstract class RedisTemplateAbstract<T extends RedisModel> extends RedisTemplate<String, T>{

    /**
     * 泛型的对应类
     */
    protected Class<T> clazz;

    // ---------------------------------------------------------------------------------------------
    // constructor
    @SuppressWarnings(value = "unchecked")
    public RedisTemplateAbstract() { // 这个无参构造函数，会由子类默认实现
        // 利用反射获取泛型类的实例，看起来很麻烦
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clazz = (Class<T>) type.getActualTypeArguments()[0];
        this.init();
    }

    /**
     * 初始化序列化工具，子类可以重写该方法进行序列化反序列方法设置
     */
    protected void init() {
        // 初始化序列化工具
        Jackson2JsonRedisSerializer<T> jackson2JsonRedisSerializer
                = new Jackson2JsonRedisSerializer<>(this.clazz);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 初始化redisTemplate
        setKeySerializer(new StringRedisSerializer());
        setValueSerializer(jackson2JsonRedisSerializer);
        setHashKeySerializer(stringRedisSerializer);
        setHashValueSerializer(jackson2JsonRedisSerializer);
    }
    // ---------------------------------------------------------------------------------------------
    // setters
    @Autowired // 注入RedisConnectionFactory
    public void setRedisConnectionFactory(RedisConnectionFactory redisConnectionFactory) {
        super.setConnectionFactory(redisConnectionFactory);
        afterPropertiesSet();
    }

    // ---------------------------------------------------------------------------------------------
    // redis 封装操作部分,其实这里可以封装全部opsForXX，看情况处理
    // list 操作
    /**
     * 根据key 查找全部redis list下全部value
     * @param key redis key
     * @return list集合value
     */
    public List<T> findAllOpsList(String key) {
        return this.opsForList().range(key, 0, -1);
    }

    // -------------------------------------------------------------------------------------------
    // set 操作
    /**
     * 根据 key 查找全部redis set下全部value
     * @param key redis key
     * @return set集合value
     */
    public Set<T> findAllOpsSet(String key) {
        return this.opsForSet().members(key);
    }

    /**
     * 往redis set 添加value
     * @param key key
     * @param collection 集合
     * @return 返回数据
     */
    @SuppressWarnings("unchecked")
    public Long opsSetAdd(String key, Collection<T> collection) {
        T[] values = (T[])Array.newInstance(clazz, collection.size());
        return this.opsSetAdd(key, collection.toArray(values));
    }

    /**
     * 往redis set 添加value
     * @param key key
     * @param values 不定参数value
     * @return 返回数据
     */
    public Long opsSetAdd(String key, T... values) {
        return this.opsForSet().add(key, values);
    }
}
