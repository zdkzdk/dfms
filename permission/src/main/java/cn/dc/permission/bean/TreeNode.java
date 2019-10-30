package cn.dc.permission.bean;

import java.io.Serializable;

/**
 * VO
 *  用来封装前台zTree的数据
 */
public class TreeNode implements Serializable {
    private long id;
    private long pId;
    private boolean isParent;
    private String name;
    private String open;//true和false

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getpId() {
        return pId;
    }

    public void setpId(long pId) {
        this.pId = pId;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public TreeNode() {
    }
}
