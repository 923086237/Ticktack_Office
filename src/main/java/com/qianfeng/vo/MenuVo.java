package com.qianfeng.vo;

import java.util.List;

import com.qianfeng.entity.Authority;

public class MenuVo {
	private int id;
	private String title;
	private List<Authority> childs;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Authority> getChilds() {
		return childs;
	}
	public void setChilds(List<Authority> childs) {
		this.childs = childs;
	}
}
