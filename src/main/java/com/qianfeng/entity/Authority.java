package com.qianfeng.entity;

import java.util.List;

import com.qianfeng.entity.Authority;

public class Authority {
    private Integer id;

    private String aicon;

    private String aurl;

    private Integer parentid;

    private String title;

    private Integer type;
    
    private List<Authority> authority;
    
    public List<Authority> getAuthority() {
		return authority;
	}

	public void setAuthority(List<Authority> authority) {
		this.authority = authority;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAicon() {
        return aicon;
    }

    public void setAicon(String aicon) {
        this.aicon = aicon;
    }

    public String getAurl() {
        return aurl;
    }

    public void setAurl(String aurl) {
        this.aurl = aurl;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}