package com.baizhi.ems_vue;

import com.baizhi.ems_vue.domain.User;
import com.baizhi.ems_vue.mapper.UserMapper;
import org.apache.ibatis.cache.Cache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
class EmsVueApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    void contextLoads() {

        userMapper.deleteById(1);
    }
    @Test
    void contentLo() {
        Cache cache;
        User user = userMapper.selectById(2);

        System.out.println("------------------------------");

        User user2 = userMapper.selectById(2);
        System.out.println(user2);
    }

}
