package com.jim.base.realm;

import com.jim.mapper.AdminMapper;
import com.jim.mapper.RepairmanMapper;
import com.jim.model.Admin;
import com.jim.model.Repairman;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

public class AdminRealm extends AuthorizingRealm {
    @Resource
    private AdminMapper adminMapper;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("AdminRealm 授权");
        SimpleAuthorizationInfo info =new SimpleAuthorizationInfo();
        Set<String> set = new HashSet<>();
        set.add("Admin");
        // 字符串资源
        info.setRoles(set);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("AdminRealm 认证");
        UsernamePasswordToken token= (UsernamePasswordToken)authenticationToken;
        Admin admin = adminMapper.selectById(token.getUsername());
        // 判断用户不存在
        if (admin==null){
            return null;
        }

        // 密码认证shiro做了
        return new SimpleAuthenticationInfo(admin,admin.getPassword(),"AdminRealm");
    }
}
