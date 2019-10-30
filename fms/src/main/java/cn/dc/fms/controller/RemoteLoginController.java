package cn.dc.fms.controller;

import cn.dc.fms.service.dwr.loginService;
import cn.dc.permission.pojo.UpmsPermission;
import cn.dc.permission.pojo.UpmsRole;
import cn.dc.permission.pojo.UpmsUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.session.HttpServletSession;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Controller
public class RemoteLoginController {
    @Autowired
    private loginService loginService;
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping("remoteLogin")
    @ResponseBody
    public String remoteLogin(HttpSession session){


        //不能直接调用loginService.login
        String string1 = loginService.login("a", "a", 0);

        return "/loginSuccess.jsp";
    }
    @RequestMapping("remoteLoginTo")
    public String remoteLoginTo(HttpSession session){

        redisTemplate.setKeySerializer(new Jackson2JsonRedisSerializer<String>(String.class));
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<UpmsUser>(UpmsUser.class));
        UpmsUser user =  (UpmsUser) redisTemplate.opsForValue().get("admin");
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Integer>(Integer.class));
        int userType = (int) redisTemplate.opsForValue().get("userType");

        session.setAttribute("admin", user);

        if (userType == 0){
            session.setAttribute("userType", 0);
        }else if(userType == 1){
            session.setAttribute("userType", 1);
        }
        //将登录用户的权限分别放到session中，然后在页面通过EL判断是否有权限
        for (UpmsRole role : user.getRoles()) {
            for (UpmsPermission permission : role.getPermissions()) {
                session.setAttribute(permission.getPermissionValue(),true);
            }
        }
        //???遍历枚举
        //Enumeration<String> enumeration = session.getAttributeNames();
        //System.out.println(enumeration.toString());
        //while (enumeration.hasMoreElements()){
        //    System.out.println(enumeration.nextElement());
        //};
        return "index";
    }
}
