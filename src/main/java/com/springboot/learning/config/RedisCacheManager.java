package com.springboot.learning.config;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @Description:
 * @Author: 坚持的力量
 * @Date: 2021/10/21 14:54
 * @Version: 11
 */

public class RedisCacheManager implements CacheManager {
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        System.out.println("RedisCacheManager=========="+s);
        return new ShiroCache<K, V>(s);
    }
}
