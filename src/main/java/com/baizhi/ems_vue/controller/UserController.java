package com.baizhi.ems_vue.controller;

import com.baizhi.ems_vue.domain.User;
import com.baizhi.ems_vue.service.UserService;
import com.baizhi.ems_vue.util.VerifyCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: 坚持的力量
 * @Date: 2021/10/4 16:27
 * @Version: 11
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    
    private static HashMap hashMap = new HashMap();
    @Resource
    private UserService userService;


    @RequestMapping ("/vercode")
    public void code(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //创建验证码生成器实例取得生成图片和随机字符串
        VerifyCode vc = new VerifyCode();
        BufferedImage image = vc.getImage();
        String text = vc.getText();
        //随机字符串存入session中
        HttpSession session = req.getSession();
        session.setAttribute("index_code",text);
        //用流传输
        VerifyCode.output(image,resp.getOutputStream());
    }
    /**
     * @Description:注册页面
     **/
    @PostMapping(value = "/register")
    public HashMap register(HttpServletRequest req, @RequestBody Map<String,Object> param,HttpSession session){
        String code = (String) param.get("code");
        HashMap<String,String> user2 = (HashMap) param.get("user");

        User user = new User();
        user.setUsername(user2.get("username")).setRealname(user2.get("realname")).setSex(user2.get("sex")).setPassword(user2.get("password"));
        String indexCode = (String) req.getSession().getAttribute("index_code");
        if (indexCode.equalsIgnoreCase(code)) {

            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username",user.getUsername());
            int count = userService.count(wrapper);
            if (count > 0){
                hashMap.put("msg","该用户已存在！");
                hashMap.put("status",500);
                return hashMap;
            }

            user.setStatus("已激活");
            boolean save = userService.save(user);
            if (save){
                authen(user.getUsername(), user.getPassword());

                hashMap.put("msg","用户保存成功！");
                hashMap.put("status",200);
            }else {
                hashMap.put("msg","用户保存失败！");
                hashMap.put("status",500);

            }

        } else {
            hashMap.put("msg","验证码输入错误！");
            hashMap.put("status",500);


        }
        return hashMap;
    }

    /**
     * @Description:登陆页面
     **/
    @PostMapping(value = "/login")
    public HashMap login(@RequestBody Map<String,String> param){
        String username = param.get("username");
        String password = param.get("password");

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username).eq("password",password);
        int count = userService.count(wrapper);
        if (count > 0){
            authen(username, password);
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.hasRole("admin");
            } catch (Exception e) {
                System.out.println("该用户为普通用户！");
            } finally {
                hashMap.put("msg","登陆成功！");
                hashMap.put("status",200);
            }

        } else {
            hashMap.put("msg","登陆失败,不存在该用户或用户密码错误!");
            hashMap.put("status",500);
        }
        return hashMap;


    }

    /**
     * @Description:安全退出
     **/
    @RequestMapping("/logout")
    public void logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    /**
     * @Description:认证
     **/
    private void authen(String username, String password){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
        
    }

}
