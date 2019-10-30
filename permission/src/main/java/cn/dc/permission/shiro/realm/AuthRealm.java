package cn.dc.permission.shiro.realm;

import cn.dc.permission.pojo.UpmsPermission;
import cn.dc.permission.pojo.UpmsRole;
import cn.dc.permission.pojo.UpmsUser;
import cn.dc.permission.service.impl.UserServiceImplLocal;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AuthRealm extends AuthorizingRealm {
    @Autowired
    private UserServiceImplLocal userService;

    //认证.登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken utoken=(UsernamePasswordToken) token;//获取用户输入的token
        String account = utoken.getUsername();
        UpmsUser user = userService.retUserByAccount(account);

        return new SimpleAuthenticationInfo(user, user.getPassword(),this.getClass().getName());//放入shiro.调用CredentialsMatcher检验密码
    }
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        UpmsUser user=(UpmsUser) principal.fromRealm(this.getClass().getName()).iterator().next();//获取session中的用户
        List<String> permissions=new ArrayList<>();
        Set<UpmsRole> roles = user.getRoles();
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        List<String> listrole = new ArrayList<>();
        if(roles.size()>0) {
            for(UpmsRole role : roles) {
                if(!listrole.contains(role.getName())){
                    listrole.add(role.getName());
                }
                Set<UpmsPermission> modules = role.getPermissions();
                if(modules.size()>0) {
                    for(UpmsPermission module : modules) {
                        permissions.add(module.getName());
                    }
                }
            }
        }
        info.addRoles(listrole);                       //授权角色.
        info.addStringPermissions(permissions);         //授权权限.
        return info;
    }

}


