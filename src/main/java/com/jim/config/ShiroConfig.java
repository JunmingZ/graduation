package com.jim.config;

import com.jim.base.realm.StudentRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        /**
         * Shiro内置过滤器，可以实现权限相关的拦截器
         * 常用的过滤器:
         *      anon:无需认证(登录)可以访问
         *      authc:必须认证才可以访问
         *      user:如果使用rememberMe的功能可以直接访问
         *      perms:该资源必须得到资源权限才可以访问
         *      role:该资源必须得到角色权限才可以访问
         */
        // 使用LinkedHashMap有序,因为该过滤器有优先级之分
        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/css/**","anon");
        filterMap.put("/js/**","anon");
        filterMap.put("/xadmin/**","anon");
        filterMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        // 设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("studentRealm") StudentRealm studentRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(studentRealm);
        return securityManager;
    }


    /**
     * 创建Realm
     * @return
     */
    @Bean(name = "studentRealm")
    public StudentRealm getStudentRealm(){
        return new StudentRealm();
    }
}
