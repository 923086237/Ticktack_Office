package com.qianfeng.service;

import java.util.List;

import com.qianfeng.entity.Role;

public interface IRoleService {
	public List<Role> findRoleAll();
	
	public void addRoleService(Integer id, String rid);
}
