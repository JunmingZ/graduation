package com.jim.base.realm;

import com.jim.config.UserToken;
import com.jim.mapper.StudentMapper;
import com.jim.model.Student;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * 自定义Realm
 */
public class StudentRealm extends AuthorizingRealm {

    @Resource
    private StudentMapper studentMapper;
    /**
     * 授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("StudentRealm doGetAuthorizationInfo ========> 授权");
        // 给资源进行授权
        SimpleAuthorizationInfo info =new SimpleAuthorizationInfo();


        return info;
    }

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("StudentRealm doGetAuthenticationInfo ========> 认证");
        UsernamePasswordToken token= (UsernamePasswordToken)authenticationToken;

        Student student = studentMapper.selectById(token.getUsername());
        // 判断用户不存在
        if (student==null){
            return null;
        }
        // 判断学生是否通过审核
        if(student.getFlag()!=1){
            return null;
        }
        // 密码认证shiro做了
        return new SimpleAuthenticationInfo(student,student.getPassword(),"StudentRealm");
    }
}
