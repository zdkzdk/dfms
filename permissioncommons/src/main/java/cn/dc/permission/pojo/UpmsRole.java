package cn.dc.permission.pojo;

import java.io.Serializable;
import java.util.Set;

public class UpmsRole implements Serializable {

    private static final long serialVersionUID = 3339871588627912039L;
    /*
        自定义
         */
    private Set<UpmsPermission> permissions;



    private Integer roleId;

    private String name;

    private String title;

    private String description;

    private Long ctime;

    private Long orders;

    public Set<UpmsPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<UpmsPermission> permissions) {
        this.permissions = permissions;
    }
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }
}