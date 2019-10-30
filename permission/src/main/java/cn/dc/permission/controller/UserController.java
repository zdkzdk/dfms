package cn.dc.permission.controller;

import cn.dc.permission.bean.TreeNode;
import cn.dc.permission.mapper.UpmsPermissionMapper;
import cn.dc.permission.mapper.UpmsRoleMapper;
import cn.dc.permission.mapper.UpmsUserMapper;
import cn.dc.permission.mapper.UpmsUserRoleMapper;
import cn.dc.permission.pojo.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UpmsUserMapper upmsUserMapper;
    @Autowired
    private UpmsRoleMapper roleMapper;

    @Autowired
    private UpmsUserRoleMapper userRoleMapper;

    @RequestMapping("reg")
    public String reg(UpmsUser upmsUser,Model model){
        try {
            upmsUser.setSalt(upmsUser.getUsername());
            upmsUser.setLocked((byte) 0);
            upmsUserMapper.insert(upmsUser);
            model.addAttribute("msg", "注册成功，请登录");
            return "login";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "注册失败");
            return "signup";
        }
    }
    @RequestMapping("login")
    public String login1(UpmsUser upmsUser, RedirectAttributes model){
        UpmsUserExample example = new UpmsUserExample();
        UpmsUserExample.Criteria criteria = example.createCriteria();
        int hashIterations = 50;
        SimpleHash sh = new SimpleHash("md5", upmsUser.getPassword(), upmsUser.getUsername(), hashIterations);
        criteria.andUsernameEqualTo(upmsUser.getUsername());
        criteria.andPasswordEqualTo(sh.toHex());
        List<UpmsUser> list = upmsUserMapper.selectByExample(example);
        System.out.println(list);
        if (list != null && list.size() > 0) {
            if (list.get(0).getRealname().equals("dc")){
                return "index";
            }

        }
        model.addFlashAttribute("msg", "登陆失败");
        return "redirect:/login";
    }
    @RequestMapping("list")
    public String list(Model model){
        UpmsUserExample example = new UpmsUserExample();

        List<UpmsUser> list = upmsUserMapper.selectByExample(example);
        System.out.println(list);
        if (list != null && list.size() > 0) {
            model.addAttribute("list", list);
            return "userlist";
        }
        return "redirect:/500.html";
    }
    /*
    回显，用户数据 + 所有角色 + 用户拥有的角色
     */
    @RequestMapping("upd/show")
    @ResponseBody
    public Map<String, Object> updShow(Model model,int userId){
        Map<String, Object> map = new HashMap<>();
        //用户数据
        UpmsUser user = upmsUserMapper.selectByPrimaryKey(userId);
        //所有角色
        UpmsRoleExample example1 = new UpmsRoleExample();
        List<UpmsRole> roleList = roleMapper.selectByExample(example1);
        List<TreeNode> listNode = new ArrayList<>();
        for (UpmsRole role : roleList) {
            TreeNode node = new TreeNode();
            node.setId(role.getRoleId());
            node.setName(role.getName());
            node.setpId(0);
            listNode.add(node);
        }
        //用户拥有的角色
        List<UpmsRole> roleSelf = upmsUserMapper.getRolesByUserId(userId);
        map.put("user", user);
        map.put("roles", listNode);
        map.put("roleSelf", roleSelf);
        return map;
    }
    /*
    更新 user和user_role中间表
     */
    @RequestMapping("upd")
    public String upd(Model model,UpmsUser user,@RequestParam("roleIdsUpd") List<Integer> roleIds){

        upmsUserMapper.updateByPrimaryKeySelective(user);
        //先删后加
        UpmsUserRoleExample example = new UpmsUserRoleExample();
        UpmsUserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(user.getUserId());
        userRoleMapper.deleteByExample(example);
        UpmsUserRole userRole = null;
        for (Integer id : roleIds){
            userRole = new UpmsUserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(id);
            userRoleMapper.insert(userRole);
        }
        return "redirect:/user/list";
    }

    @RequestMapping("del")
    public String del(Model model, int userId) {
        userRoleMapper.delURByUserId(userId);
        upmsUserMapper.deleteByPrimaryKey(userId);

        return "redirect:/user/list";
    }
}
