package cn.dc.permission.service.impl;

import cn.dc.permission.mapper.UpmsUserMapper;
import cn.dc.permission.pojo.UpmsUser;
import cn.dc.permission.pojo.UpmsUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplLocal {
    @Autowired
    private UpmsUserMapper userMapper;
    /*
        根据account查user
        */
    public UpmsUser retUserByAccount(String account) {
        UpmsUserExample example = new UpmsUserExample();
        UpmsUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(account);
        return userMapper.selectByExample(example).get(0);
    }
}
