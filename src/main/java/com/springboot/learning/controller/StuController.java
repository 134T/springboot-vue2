package com.springboot.learning.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.learning.domain.Stu;
import com.springboot.learning.mapper.StuRoleMapper;
import com.springboot.learning.service.StuService;
import com.springboot.learning.util.Md5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * @Description:
 * @Author: 坚持的力量
 * @Date: 2021/10/17 10:02
 * @Version: 11
 */
@Controller
@RequestMapping("/user")
public class StuController {

    @Resource
    private StuService stuService;


    /**
     * @Description:第一次要从这里登陆
     **/
    @RequestMapping("/l")
    public String loginFirst(){
        return "login";
    }
    /**
     * @Description:第一次要从这里注册
     **/
//    @RequestMapping("/r")
//    public String registFirst(){
//        return "regist";
//    }

    /**
     * @Description:登出
     **/
    @RequestMapping("/logout")
    public ModelAndView logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("msg","注销用户成功！");
        return modelAndView;
    }
    /**
     * @Description:登陆页面
     **/
    @RequestMapping("/login")
    public ModelAndView login(String username, String password){

        ModelAndView modelAndView = new ModelAndView();

        QueryWrapper<Stu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Stu stu = stuService.getOne(queryWrapper);
        if(stu == null){
            modelAndView.setViewName("login");
            modelAndView.addObject("msg","该用户不存在！");
            return modelAndView;
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            modelAndView.setViewName("index");
            if (subject.hasRole("philosopher")){

                modelAndView.addObject("msg","登陆成功！");
            }

        } catch (AuthenticationException e) {
            modelAndView.setViewName("login");
            modelAndView.addObject("msg","用户名或密码错误！");
            e.printStackTrace();
        }


        return modelAndView;


    }

    /**
     * @Description:注册页面
     **/
    @RequestMapping("/regist")
    public ModelAndView regist(String username, String password, String a){


        ModelAndView modelAndView = new ModelAndView();
        if ("aa".equals(a)){
            modelAndView.setViewName("regist");
            modelAndView.addObject("msg","请注册！");
            return modelAndView;
        }
        if (username == null || password == null){

            modelAndView.setViewName("regist");
            modelAndView.addObject("msg","用户或密码不能为空！");
            return modelAndView;

        }

        QueryWrapper<Stu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Stu stu = stuService.getOne(queryWrapper);
        if(stu != null){

            modelAndView.setViewName("regist");
            modelAndView.addObject("msg","该用户已存在");
            return modelAndView;
        }

        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole("politician")){
            Stu stu1 = new Stu();
            stu1.setUsername(username);
            stu1.setSalt(Md5Utils.getRandomString(16));
            Md5Hash md5Hash = new Md5Hash(password, stu1.getSalt(), 1024);
            stu1.setPassword(md5Hash.toString());
            stuService.save(stu1);
            modelAndView.setViewName("index");
            modelAndView.addObject("msg","用户注册成功");
        }else {
            modelAndView.setViewName("index");
            modelAndView.addObject("msg","对不起，该权限只拥有politician角色的用户才有！");
        }

        return modelAndView;
    }
}
