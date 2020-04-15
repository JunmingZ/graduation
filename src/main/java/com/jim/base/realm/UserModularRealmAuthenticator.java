package com.jim.base.realm;

import com.jim.config.UserToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 自定义Authenticator，用于多realm验证
 */
public class UserModularRealmAuthenticator extends ModularRealmAuthenticator {
	
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        System.out.println("UserModularRealmAuthenticator:method doAuthenticate() execute ");
        // 判断getRealms()是否返回为空
        assertRealmsConfigured();
        // 强制转换回自定义的UserToken
        UserToken userToken = (UserToken)authenticationToken;
        // 登录类型
        String loginType = userToken.getLoginType();
        System.out.println("loginType:"+loginType);
        // 所有Realm
        Collection<Realm> realms = getRealms();
        // 登录类型对应的所有Realm
        List<Realm> typeRealms = new ArrayList<>();
        for (Realm realm : realms) {
            if (realm.getName().contains(loginType)) {
                System.out.println("遍历出符合条件的realm.getName()："+realm.getName());
                typeRealms.add(realm);
            }
        }

        // 判断是单Realm还是多Realm
        if (typeRealms.size() == 1){
            System.out.println("doSingleRealmAuthentication() execute "+typeRealms.get(0));
            return doSingleRealmAuthentication(typeRealms.get(0), userToken);
        }
        else{
            System.out.println("doMultiRealmAuthentication() execute ");
            return doMultiRealmAuthentication(typeRealms, userToken);
        }
    }
}
