package cn.dc.permission.controller;

import cn.dc.permission.bean.TreeNode;
import cn.dc.permission.mapper.UpmsPermissionMapper;
import cn.dc.permission.mapper.UpmsRoleMapper;
import cn.dc.permission.pojo.UpmsPermission;
import cn.dc.permission.pojo.UpmsPermissionExample;
import cn.dc.permission.pojo.UpmsRole;
import cn.dc.permission.pojo.UpmsRoleExample;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private UpmsRoleMapper roleMapper;
    @Autowired
    private UpmsPermissionMapper permissionMapper;
    @RequestMapping("list")
    public String list(Model model){
        UpmsRoleExample example = new UpmsRoleExample();

        List<UpmsRole> list = roleMapper.selectByExample(example);


        Gson gson = new GsonBuilder().create();
        String string = gson.toJson(perList());
        model.addAttribute("list", list);
        model.addAttribute("roles", string);
        return "rolelist";
    }

    @RequestMapping("add")
    public String add(Model model, UpmsRole role, @RequestParam("permissionIds") List<String> permissionIds) {
        role.setCtime(new Date().getTime());
        role.setOrders(1L);
        roleMapper.insert(role);
        for (String id : permissionIds) {
            roleMapper.addPerToRole(role.getRoleId(), id);
        }
        return "redirect:/role/list";
    }
    @RequestMapping("upd/show")
    @ResponseBody
    public Map<String,Object> updShow(Model model, int roleId) {
        //返回所有的权限和根据roleId查出的role，role的permission要赋值，用来回显
        Map<String, Object> map = new HashMap<>();
        //role
        UpmsRole role = roleMapper.selectByPrimaryKey(roleId);
        //根据roleId查权限
        Set<UpmsPermission> permissionSet = permissionMapper.findPerByRoleId(roleId);
        role.setPermissions(permissionSet);
        //所有permission

        map.put("role", role);
        map.put("permissions", perList());
        return map;
    }
    @RequestMapping("upd")
    public String upd(Model model, UpmsRole role, @RequestParam("permissionIds") List<String> permissionIds) {
        role.setCtime(new Date().getTime());
        role.setOrders(1L);
        roleMapper.updateByPrimaryKey(role);
        roleMapper.delPerByRId(role.getRoleId());
        for (String id : permissionIds) {

            roleMapper.addPerToRole(role.getRoleId(), id);
        }
        return "redirect:/role/list";
    }
    @RequestMapping("del")
    public String del(Model model, int roleId) {
        roleMapper.delPerByRId(roleId);
        roleMapper.deleteByPrimaryKey(roleId);

        return "redirect:/role/list";
    }
    private List<TreeNode> perList(){
        UpmsPermissionExample exampleAll = new UpmsPermissionExample();
        List<UpmsPermission> permissionList = permissionMapper.selectByExample(exampleAll);
        List<TreeNode> listNode = new ArrayList<>();
        for (UpmsPermission permission : permissionList) {
            TreeNode nodeP = new TreeNode();
            nodeP.setId(permission.getPermissionId());
            nodeP.setName(permission.getName());

            nodeP.setpId(permission.getPid());
            listNode.add(nodeP);
        }
        return listNode;
    }
}
