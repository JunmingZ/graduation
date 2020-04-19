package com.jim.config;

import com.jim.base.realm.*;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager){
        System.out.println("进入ShiroConfig");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 存放自定义的filter
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        // 配置自定义 or角色 认证
        filtersMap.put("roles", new RoleFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);



        /**
         * Shiro内置过滤器，可以实现权限相关的拦截器
         * 常用的过滤器:
         *      anon:无需认证(登录)可以访问
         *      authc:必须认证才可以访问
         *      user:如果使用rememberMe的功能可以直接访问
         *      perms:该资源必须得到资源权限才可以访问
         *      roles:该资源必须得到角色权限才可以访问
         */
        // 使用LinkedHashMap有序,因为该过滤器有优先级之分
        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/css/**","anon");
        filterMap.put("/js/**","anon");
        filterMap.put("/xadmin/**","anon");
        filterMap.put("/login","anon");
        filterMap.put("/register/**","anon");

        filterMap.put("/admin/**","roles[Admin]");

        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterMap.put("/logout", "logout");
        filterMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        // 设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     * @return
     */
    @Bean(name = "SecurityManager")
    public SecurityManager securityManager(){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm.
        securityManager.setAuthenticator(modularRealmAuthenticator());
        List<Realm> realms = new ArrayList<>();
        //添加多个Realm
        realms.add(getStudentRealm());
        realms.add(getRepairmanRealm());
        realms.add(getAdminRealm());
        securityManager.setRealms(realms);

        //自定义ModularRealmAuthorizer，用于多realm授权
        UserModularRealmAuthorizer authorizer = new UserModularRealmAuthorizer();
        authorizer.setRealms(realms);
        securityManager.setAuthorizer(authorizer);

        return securityManager;
    }
    /**
     * 系统自带的Realm管理，主要针对多realm
     * */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator(){
        //自己重写的ModularRealmAuthenticator
        UserModularRealmAuthenticator modularRealmAuthenticator = new UserModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    /**
     * 创建Realm
     * @return
     */
    @Bean(name = "studentRealm")
    public StudentRealm getStudentRealm(){
        return new StudentRealm();
    }

    /**
     * 创建Realm
     * @return
     */
    @Bean(name = "repairmanRealm")
    public RepairmanRealm getRepairmanRealm(){
        return new RepairmanRealm();
    }

    /**
     * 创建Realm
     * @return
     */
    @Bean(name = "adminRealm")
    public AdminRealm getAdminRealm(){
        return new AdminRealm();
    }
}
