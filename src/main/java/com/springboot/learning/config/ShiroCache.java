package com.springboot.learning.config;

import com.springboot.learning.util.SpringUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collection;
import java.util.Set;

/**
 * @Description:
 * @Author: 坚持的力量
 * @Date: 2021/10/21 14:41
 * @Version: 11
 */

public class ShiroCache<K, V> implements Cache<K, V> {

    private String cacheName;

    public ShiroCache(String cacheName) {
        this.cacheName = cacheName;
    }

    public ShiroCache() {
    }

    private RedisTemplate getRedisTemplate(){

        RedisTemplate redisTemplate = (RedisTemplate) SpringUtils.getBean("redisTemplate");

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;

    }

    @Override
    public V get(K k) throws CacheException {
        return (V) getRedisTemplate().opsForHash().get(this.cacheName, k.toString());

    }

    @Override
    public V put(K k, V v) throws CacheException {
        getRedisTemplate().opsForHash().put(this.cacheName, k.toString(),v);
        return null;
    }

    @Override
    public V remove(K k) throws CacheException {
        getRedisTemplate().opsForHash().delete(this.cacheName,k.toString());
        return null;
    }

    @Override
    public void clear() throws CacheException {

        getRedisTemplate().opsForHash().delete(this.cacheName);

    }

    @Override
    public int size() {

        return getRedisTemplate().opsForHash().size(this.cacheName).intValue();
    }

    @Override
    public Set<K> keys() {
        return getRedisTemplate().opsForHash().keys(this.cacheName);
    }

    @Override
    public Collection<V> values() {

        return getRedisTemplate().opsForHash().values(this.cacheName);
    }
}
