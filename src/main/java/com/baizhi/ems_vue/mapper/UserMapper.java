package com.baizhi.ems_vue.mapper;

import com.baizhi.ems_vue.config.MybatisRedisCache;
import com.baizhi.ems_vue.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author asus
 * @Entity com.baizhi.ems_vue.domain.User
 */
@CacheNamespace(implementation=MybatisRedisCache.class,eviction=MybatisRedisCache.class)
@Transactional(rollbackFor = RuntimeException.class)
public interface UserMapper extends BaseMapper<User> {
}




