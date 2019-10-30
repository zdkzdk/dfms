package cn.dc.permission.service.impl;

import cn.dc.permission.mapper.UpmsPermissionMapper;
import cn.dc.permission.mapper.UpmsRoleMapper;
import cn.dc.permission.mapper.UpmsUserMapper;
import cn.dc.permission.pojo.UpmsPermission;
import cn.dc.permission.pojo.UpmsRole;
import cn.dc.permission.pojo.UpmsUser;
import cn.dc.usercenterservice.service.UserService;
import org.apache.dubbo.config.annotation.Service;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 为用户中心提供注册和登录验证的服务
 */
@Service(timeout = 100000)
public class UserServiceImpl implements UserService {

    @Autowired
    private UpmsRoleMapper roleMapper;
    @Autowired
    private UpmsUserMapper userMapper;
    @Resource
    private SecurityManager securityManager;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UpmsPermissionMapper permissionMapper;


    @Override
    public int addUser(UpmsUser upmsUser) {
        upmsUser.setSalt(upmsUser.getUsername());
        SimpleHash sh = new SimpleHash("md5", upmsUser.getPassword(), upmsUser.getUsername(), 50);
        upmsUser.setPassword(sh.toHex());
        userMapper.insert(upmsUser);
        return 0;
    }

    /*
    使用shiro登录验证
     */
    public String loginValidate(UpmsUser upmsUser,boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(upmsUser.getUsername(), upmsUser.getPassword(),rememberMe);

        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        try {

            subject.login(token);
            //手动在reids中设置session
            UpmsUser user = (UpmsUser)(subject.getPrincipal());
            sq(user);
            //登录成功，在redis中添加登录信息
            redisTemplate.setKeySerializer(new Jackson2JsonRedisSerializer<String>(String.class));
            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<UpmsUser>(UpmsUser.class));
            redisTemplate.opsForValue().set("admin", user);
            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Integer>(Integer.class));
            redisTemplate.opsForValue().set("userType", 0);
            /*在本地可以实现：
                登录后获取存在session中的用户对象
                isAuthenticated()为true*/
            //System.out.println(" SecurityUtils.getSubject().isAuthenticated() : " +  SecurityUtils.getSubject().isAuthenticated());
            //System.out.println(" UpmsUser : " +  ((UpmsUser)(SecurityUtils.getSubject().getPrincipal())).getUsername());
        } catch (IncorrectCredentialsException e) {
            return "密码错误";
        } catch (LockedAccountException e) {
            return "登录失败，该用户已被冻结";
        } catch (AuthenticationException e) {
            return "该用户不存在";
        }
        //登录成功，会自动将信息放到shiro 的session中，也就是redis中
        System.out.println("shiroSessionId =" + SecurityUtils.getSubject().getSession().getId());

        return "success";
    }

    private void sq(UpmsUser user){
        //用户拥有的role
        List<UpmsRole> roleSelf = userMapper.getRolesByUserId(user.getUserId());
        for (UpmsRole role : roleSelf) {
            Set<UpmsPermission> permissionSet = permissionMapper.findPerByRoleId(role.getRoleId());
            role.setPermissions(permissionSet);
        }
        user.setRoles(new HashSet<>(roleSelf));
    }


}
