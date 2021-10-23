package com.springboot.learning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.learning.domain.Stu;
import com.springboot.learning.service.StuService;
import com.springboot.learning.mapper.StuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class StuServiceImpl extends ServiceImpl<StuMapper, Stu>
implements StuService{

}




