package com.baizhi.ems_vue.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baizhi.ems_vue.domain.User;
import com.baizhi.ems_vue.service.UserService;
import com.baizhi.ems_vue.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * @author asus
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
implements UserService{


}




