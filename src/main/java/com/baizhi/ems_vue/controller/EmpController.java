package com.baizhi.ems_vue.controller;

import com.baizhi.ems_vue.domain.Emp;
import com.baizhi.ems_vue.service.EmpService;
import com.baizhi.ems_vue.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @Description:
 * @Author: 坚持的力量
 * @Date: 2021/10/5 12:26
 * @Version: 11
 */
@RestController
@CrossOrigin
@RequestMapping("/emp")
@Slf4j
public class EmpController {

    private static HashMap<String, Object> hashMap = new HashMap();
    @Resource
    private EmpService empService;


    /**
     * @Description:获取数据
     **/

    @RequestMapping("/getAll")
    public List<Emp> getAll(){
        List<Emp> list = empService.list();
        return list;

    }
    /**
     * @Description:删除员工
     **/
    @DeleteMapping("/delEmp")
    public HashMap delEmp(Integer id){

        Subject subject = SecurityUtils.getSubject();
        if ( subject.hasRole("admin")){

            empService.removeById(id);
            hashMap.put("status",true);
            hashMap.put("msg","删除成功！");
        }else {
            hashMap.put("status",false);
            hashMap.put("msg","对不起，该权限只有管理员才有！");
        }

        return hashMap;


    }
    /**
     * @Description:根据员工id获取员工数据,并设置session的值
     **/
    @RequestMapping("/getById")
    public HashMap getById(Integer id){
        Emp emp = empService.getById(id);
        hashMap.put("emp", emp);

        return hashMap;

    }

    /**
     * @Description:保存修改
     **/
    @PostMapping("/saveEdit")
    public HashMap<String, Object> saveEdit(Emp emp, MultipartFile photo) {

        if (photo != null){
            String originalFilename = photo.getOriginalFilename();
            try {
                String pathName = "D:\\idea\\project\\baizhi\\ems_vue\\src\\main\\resources\\static\\img\\"+originalFilename;
                photo.transferTo(new File(pathName));
                emp.setPath(originalFilename);
                empService.updateById(emp);
                hashMap.put("status",true);
                hashMap.put("msg", "用户保存成功");
            } catch (IOException e) {
                hashMap.put("status",false);
                hashMap.put("msg", "用户保存失败");
                e.printStackTrace();

            }

        }else {
            empService.updateById(emp);
            hashMap.put("status",true);
            hashMap.put("msg", "用户保存成功");
        }
        return hashMap;

    }
    /**
     * @Description:添加emp
     **/
    @PostMapping("/saveEmp")
    public HashMap<String, Object> saveEmp(Emp emp, MultipartFile photo){


        if (!photo.isEmpty()){
            String originalFilename = photo.getOriginalFilename();
            try {
                String pathName = "D:\\idea\\project\\baizhi\\ems_vue\\src\\main\\resources\\static\\img\\"+originalFilename;
                photo.transferTo(new File(pathName));
                emp.setPath(originalFilename);
                empService.save(emp);
                hashMap.put("status",true);
                hashMap.put("msg", "用户保存成功");
            } catch (IOException e) {
                hashMap.put("status",false);
                hashMap.put("msg", "用户保存失败");
                e.printStackTrace();

            }

        }
        return hashMap;
    }
}
