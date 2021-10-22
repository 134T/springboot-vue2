package com.baizhi.ems_vue.config;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: 坚持的力量
 * @Date: 2021/10/22 11:18
 * @Version: 11
 */
@Configuration
public class ShiroConfig {

    /**
     * @Description:创建shiroFilter
     **/
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){

        //创建shiro的filter
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给filter注入安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        //默认认证界面的路径
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //配置系统资源
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

//        filterChainDefinitionMap.put("/addEmp.html","anon");
        filterChainDefinitionMap.put("/regist.html","anon");
//        filterChainDefinitionMap.put("/emplist.html","anon");
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/img/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/user/**","anon");
//        filterChainDefinitionMap.put("/emp/**","anon");

        filterChainDefinitionMap.put("/**","authc");

        //配置认证和授权规则
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }
    /**
     * @Description:创建web管理器
     **/
    @Bean
    public DefaultWebSecurityManager securityManager() {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(getRealm());
        return securityManager;
    }


    /**
     * @Description:创建自定义realm
     **/
    @Bean
    public CustomRealm getRealm() {

        return new CustomRealm();
    }


    /**
     * @Description:开启shiro注解支持
     **/
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }


    /**
     * @Description:开启aop注解支持
     **/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        // 这里需要注入 SecurityManger 安全管理器
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }


}
