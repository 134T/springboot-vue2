package com.springboot.learning.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
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
 * @Date: 2021/10/16 9:57
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
        shiroFilterFactoryBean.setLoginUrl("/user/l");
        //配置系统资源
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/user/r","roles[philosopher]");

        filterChainDefinitionMap.put("/user/**","anon");
        filterChainDefinitionMap.put("/**","authc");

        //配置认证和授权规则
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }
    /**
     * @Description:创建web管理器
     **/
    @Bean
    public SecurityManager securityManager() {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(getRealm());
        return securityManager;
    }


    /**
     * @Description:创建自定义realm
     **/
    @Bean
    public CustomRealm getRealm() {

        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());

        //开启缓存管理
        customRealm.setCacheManager(new RedisCacheManager());
        //开启全局缓存
        customRealm.setCachingEnabled(true);
        //开启认证缓存
        customRealm.setAuthenticationCachingEnabled(true);
        customRealm.setAuthenticationCacheName("AuthenticationCache");
        //开启授权缓存
        customRealm.setAuthorizationCachingEnabled(true);
        customRealm.setAuthorizationCacheName("AuthorizationCache");
        return customRealm;
    }


    /**
     * @Description:加密方式
     **/
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){

        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置散列算法 ：这里设置的MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置多重加密算法 ：这里设置的是1024次加密
        credentialsMatcher.setHashIterations(1024);
        return credentialsMatcher;
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
     * 配置ShiroDialect，用于Shiro和thymeleaf标签配合使用
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }


//    @Bean
//    public RedisTemplate redisTemplate(){
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        return redisTemplate;
//    }


}
