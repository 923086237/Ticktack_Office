package com.qianfeng.dao;

import java.util.List;
import java.util.Map;

import com.qianfeng.entity.Role;
import com.qianfeng.entity.RoleAuthority;
import com.qianfeng.vo.MapBean2;

public interface IRoleDao {
	public List<Role> findRoleAll();

	public void addRole(List<MapBean2> mapBeans);
	
	public void deleteRole(Integer id);
}
