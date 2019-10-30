package cn.dc.usercenterservice.service;

import cn.dc.permission.pojo.UpmsUser;
import cn.dc.usercenter.pojo.UcenterUser;

import java.util.Map;

public interface UserService {
    int addUser(UpmsUser upmsUser);



    String loginValidate(UpmsUser upmsUser,boolean rememberMe);
}
