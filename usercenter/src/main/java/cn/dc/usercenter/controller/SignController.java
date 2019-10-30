package cn.dc.usercenter.controller;

import cn.dc.permission.pojo.UpmsUser;
import cn.dc.usercenterservice.service.LoginToService;
import cn.dc.usercenterservice.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class SignController extends BaseController {

    @Reference(mock = "return null")
    private UserService userService;
    @Reference(mock = "return null")
    private LoginToService loginToService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping({"signup", "signin"})
    public String page(Model model, UpmsUser upmsUser) {

        return thymeleaf("login");
    }

    @RequestMapping({"login"})
    public String login(Model model, UpmsUser upmsUser, @RequestParam(value = "rememberMe", defaultValue = "false") Boolean rememberMe) throws URISyntaxException {
        //远程调用
        String msg = userService.loginValidate(upmsUser, rememberMe);
        System.out.println("userService.loginValidate返回值：" + msg);
        //登录成功，跳转到2000的fms项目
        if ("success".equals(msg)) {
            URI uri = new URI("http://localhost:3000/remoteLogin");
            String string = restTemplate.getForObject(uri, String.class);
            return "redirect:http://localhost:3000/remoteLoginTo";
        }
        //登录失败
        model.addAttribute("upmsUser", upmsUser);
        model.addAttribute("msg", msg);
        return thymeleaf("login");
    }

    @RequestMapping(value = "reg", method = RequestMethod.GET)
    public String reg(Model model) {

        return thymeleaf("reg");
    }

    @RequestMapping(value = "reg", method = RequestMethod.POST)
    public String reg(Model model, UpmsUser upmsUser) {
        userService.addUser(upmsUser);

        return thymeleaf("login");
    }
}
