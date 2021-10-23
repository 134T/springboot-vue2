package com.springboot.learning;

import com.springboot.learning.domain.Roles;
import com.springboot.learning.domain.Stu;
import com.springboot.learning.domain.StuRole;
import com.springboot.learning.mapper.StuRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
class LearningApplicationTests {

    @Resource
    private StuRoleMapper stuRoleMapper;

    @Test
    void contextLoads() {

        StuRole admin = stuRoleMapper.getStuRoles("a1");
        admin.getRoles().forEach(res ->{
            System.out.println(res.getRole());
        });

//        admin.forEach(ad ->{
//            System.out.println("用户=="+ad);
//            ad.getRoles().forEach(ro ->{
//                System.out.println("角色===="+ro);
//
//            });
//
//        });
//        System.out.println(admin.toString());

//        for (StuRole stu: admin){
//            System.out.println(stu);
//            List<Roles> roles = stu.getRoles();
//            for (Roles role : roles){
//                System.out.println(role);
//
//            }
//
//        }


//        admin.getRoles().forEach(System.out::println);


    }

}
