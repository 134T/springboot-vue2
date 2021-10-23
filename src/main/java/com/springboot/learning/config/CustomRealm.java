package com.springboot.learning.config;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.learning.domain.Stu;
import com.springboot.learning.domain.StuRole;
import com.springboot.learning.mapper.StuRoleMapper;
import com.springboot.learning.service.StuService;
import com.springboot.learning.util.MyByteSource;
import com.springboot.learning.util.SpringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @Description:
 * @Author: 坚持的力量
 * @Date: 2021/10/19 11:44
 * @Version: 11
 */
public class CustomRealm extends AuthorizingRealm {

    /**
     * @Description:授权
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权===========doGetAuthorizationInfo");
        //获取身份信息
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        StuRoleMapper stuRoleMapper = (StuRoleMapper) SpringUtils.getBean("stuRoleMapper");
        StuRole stuRoles = stuRoleMapper.getStuRoles(primaryPrincipal);
        if(stuRoles.getRoles() != null){
            //授权角色信息
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            stuRoles.getRoles().forEach(res ->{
                info.addRole(res.getRole());
            });
            return  info;

        }

        return null;
    }

    /**
     * @Description:认证
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        System.out.println("认证===========doGetAuthenticationInfo()");

        String principal = (String) authenticationToken.getPrincipal();
        StuService stuService= (StuService) SpringUtils.getBean("stuServiceImpl");
        QueryWrapper<Stu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", principal);
        Stu stu = stuService.getOne(queryWrapper);

        if(stu != null){

            return new SimpleAuthenticationInfo(stu.getUsername(), stu.getPassword(), new MyByteSource(stu.getSalt()),this.getName());
        }

        return null;
    }
}
