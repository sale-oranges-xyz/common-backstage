package com.github.geng.redis.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * 当redisTemple约束为类时，作为约束的父类
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedisModel implements Serializable {
    private static final long serialVersionUID = 1L;

}
