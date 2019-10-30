package cn.dc.usercenter.pojo;

import java.io.Serializable;

public class UcenterOauth implements Serializable {
    private Integer oauthId;

    private String name;

    public Integer getOauthId() {
        return oauthId;
    }

    public void setOauthId(Integer oauthId) {
        this.oauthId = oauthId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}