package com.baizhi.ems_vue.config;

import com.baizhi.ems_vue.domain.User;
import com.baizhi.ems_vue.domain.UserRole;
import com.baizhi.ems_vue.service.UserRoleService;
import com.baizhi.ems_vue.service.UserService;
import com.baizhi.ems_vue.service.impl.UserRoleServiceImpl;
import com.baizhi.ems_vue.service.impl.UserServiceImpl;
import com.baizhi.ems_vue.util.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
 * @Date: 2021/10/22 10:52
 * @Version: 11
 */

public class CustomRealm extends AuthorizingRealm {

    /**
     * @Description:管理员获取授权信息
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权===========doGetAuthorizationInfo");
        //获取身份信息
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user", primaryPrincipal);
        UserRoleService userRoleService = (UserRoleService) SpringUtil.getBean("userRoleServiceImpl");

        UserRole  userRole = userRoleService.getOne(queryWrapper);
        if (userRole != null){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole(userRole.getRole());
            return info;
        }

        return null;
    }

    /**
     * @Description:普通用户认证
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证===========doGetAuthenticationInfo()");

        String principal = (String) authenticationToken.getPrincipal();
        UserService userService = (UserService) SpringUtil.getBean("userServiceImpl");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", principal);
        User user = userService.getOne(queryWrapper);

        if(user != null){
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),this.getName());
        }

        return null;
    }

}
