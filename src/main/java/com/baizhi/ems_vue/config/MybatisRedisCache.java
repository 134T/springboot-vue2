package com.baizhi.ems_vue.config;

import com.baizhi.ems_vue.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description:
 * @Author: 坚持的力量
 * @Date: 2021/10/6 15:24
 * @Version: 11
 */

@Slf4j
public class MybatisRedisCache implements Cache {

    // 读写锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    //这里使用了redis缓存，使用springboot自动注入
    private final String id;

    public MybatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {

        if (value != null) {
            getRedisTemplate().opsForHash().put(id, key.toString(), value);
        }
    }

    @Override
    public Object getObject(Object key) {

        try {
            if (key != null) {
                return getRedisTemplate().opsForHash().get(id.toString(), key.toString());

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("缓存出错 ");
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {

        log.debug("删除缓存");
        return null;
    }

    @Override
    public void clear() {
        log.debug("清空缓存");
        getRedisTemplate().delete(id);
    }

    @Override
    public int getSize() {
        int size = getRedisTemplate().opsForHash().size(id.toString()).intValue();
        return size;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    private RedisTemplate getRedisTemplate(){
        RedisTemplate redisTemplate = (RedisTemplate) SpringUtil.getBean("redisTemplate");

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;

    }
}
