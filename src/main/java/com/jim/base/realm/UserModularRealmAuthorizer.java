package com.jim.base.realm;

import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

/**
 * 自定义ModularRealmAuthorizer，用于多realm授权
 */
public class UserModularRealmAuthorizer extends ModularRealmAuthorizer {
    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        System.out.println("自定义ModularRealmAuthorizer:hasRole");
        assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames();
        //获取realm的名字
        String realmName = realmNames.iterator().next();
        System.out.println("realmName:"+realmName);

        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) continue;
            //匹配名字
            if(realmName.equals("AdminRealm")){
                if (realm instanceof AdminRealm) {
                    return ((AdminRealm) realm).hasRole(principals, roleIdentifier);
                }
            }
            //匹配名字
            if(realmName.equals("StudentRealm")) {
                if (realm instanceof StudentRealm) {
                    return ((StudentRealm) realm).hasRole(principals, roleIdentifier);
                }
            }
            //匹配名字
            if(realmName.equals("RepairmanRealm")) {
                if (realm instanceof RepairmanRealm) {
                    return ((RepairmanRealm) realm).hasRole(principals, roleIdentifier);
                }
            }
        }

        return false;
    }
}
