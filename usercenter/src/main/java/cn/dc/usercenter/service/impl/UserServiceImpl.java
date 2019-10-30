package cn.dc.usercenter.service.impl;


import cn.dc.permission.pojo.UpmsUser;
import cn.dc.permission.pojo.UpmsUserExample;


import cn.dc.usercenter.mapper.UpmsUserMapper;
import cn.dc.usercenter.pojo.UcenterUser;
import cn.dc.usercenterservice.service.UserService;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户中心
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UpmsUserMapper userMapper;
    @Reference
    private UserService userService;


    @Override
    public int addUser(UpmsUser upmsUser) {
        userMapper.insert(upmsUser);
        return 0;
    }

    /*
        使用shiro登录验证
         */
    public String loginValidate(UpmsUser upmsUser,boolean rememberMe){
       return userService.loginValidate(upmsUser,rememberMe);
        //UsernamePasswordToken token = new UsernamePasswordToken(ucenterUser.getAccount(), ucenterUser.getPassword());

    }

    /*
     根据account查user
     */
    public UpmsUser retUserByAccount(String account) {
        UpmsUserExample example = new UpmsUserExample();
        UpmsUserExample.Criteria criteria = example.createCriteria();
        return userMapper.selectByExample(example).get(0);
    }
}
