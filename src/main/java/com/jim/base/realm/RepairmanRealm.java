package com.jim.base.realm;

import com.jim.mapper.RepairmanMapper;
import com.jim.model.Repairman;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

public class RepairmanRealm extends AuthorizingRealm {
    @Resource
    private RepairmanMapper repairmanMapper;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("RepairmanRealm 授权");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("RepairmanRealm 认证");
        UsernamePasswordToken token= (UsernamePasswordToken)authenticationToken;
        Repairman repairman = repairmanMapper.selectById(token.getUsername());
        // 判断用户不存在
        if (repairman==null){
            return null;
        }
        // 判断是否在职
        if(repairman.getFlag()!=1){
            return null;
        }
        // 密码认证shiro做了
        return new SimpleAuthenticationInfo(repairman,repairman.getPassword(),"");
    }
}
