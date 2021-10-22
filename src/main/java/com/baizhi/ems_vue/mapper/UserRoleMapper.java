package com.baizhi.ems_vue.mapper;

import com.baizhi.ems_vue.config.MybatisRedisCache;
import com.baizhi.ems_vue.domain.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author asus
 * @Entity com.baizhi.ems_vue.domain.UserRole
 */
@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
@Transactional(rollbackFor = RuntimeException.class)
public interface UserRoleMapper extends BaseMapper<UserRole> {

}




