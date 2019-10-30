package cn.dc.permission.controller;

import cn.dc.permission.bean.TreeNode;
import cn.dc.permission.mapper.UpmsPermissionMapper;
import cn.dc.permission.pojo.UpmsPermission;
import cn.dc.permission.pojo.UpmsPermissionExample;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("permission")
public class PermissionController {
    @Autowired
    private UpmsPermissionMapper permissionMapper;
    @RequestMapping("list")
    public String list(Model model){
        //所有权限的数据
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
        Gson gson = new GsonBuilder().create();
        String string = gson.toJson(listNode);
        model.addAttribute("pers", string);
        return "permissionlist";
    }
}
